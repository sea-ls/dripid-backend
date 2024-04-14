package ru.seals.delivery.service.impl;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.service.MinioService;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {
    private static final String BUCKET_NAME = "warehouse";

    @Autowired
    private MinioClient minioClient;

    @Override
    public void saveImage(MultipartFile file, String bucketName, String fileName) {
        try {
            if (!bucketExists(bucketName)) {
                throw new RuntimeException(String.format("Error occurred during uploading image." +
                        "No such bucket with name: %s", bucketName));
            }

            Map<String, String> metadata = new HashMap<>();
            metadata.put("originalFilename", file.getOriginalFilename());

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .userMetadata(metadata)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            log.info(String.format("Сохранение нового изображения с названием '%s' выполнено успешно.", imageName));
        } catch (Exception e) {
            log.error("Сохранение нового изображения не выполнено.");
            throw new RuntimeException("Error occurred during uploading image.");
        }
    }

    @Override
    public String getImage(String bucketName, String fileName) {
        try {
            Map<String, String> reqParams = new HashMap<>();
            reqParams.put("response-content-type", "image/png");
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(2) // TODO: change to ".expiry(2, TimeUnit.HOURS)". reason -> https://programingqa.com/answers/intellij-idea-jdk-21-issue-with-java-util-concurrent-package-timeunit-class-n/
                            .extraQueryParams(reqParams)
                            .build());
            return url;
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error has occurred during get image %s", fileName));
        }
    }

    @Override
    public void deleteImage(String bucketName, String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during deleting image.");
        }
    }

    private boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }
}
