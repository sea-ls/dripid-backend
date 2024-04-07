package ru.seals.delivery.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.service.MinioService;

import java.util.HashMap;
import java.util.Map;

@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioClient minioClient;

    @Override
    public void saveImage(MultipartFile file, String fileName, String bucketName) {
        try {
            if (!bucketExists(bucketName)) {
                throw new RuntimeException(String.format("Error occurred during uploading image for warehouse." +
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
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during uploading image for warehouse.");
        }
    }

    @Override
    public String getImage(String fileName, String bucketName) {
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

    private boolean bucketExists(String bucket) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
    }
}
