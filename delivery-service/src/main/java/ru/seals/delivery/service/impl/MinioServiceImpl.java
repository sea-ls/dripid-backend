package ru.seals.delivery.service.impl;

import io.minio.*;
import io.minio.http.Method;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.service.MinioService;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class MinioServiceImpl implements MinioService {
    private static final String BUCKET_NAME = "warehouse";

    @Autowired
    private MinioClient minioClient;

    @Override
    public void saveImage(MultipartFile file, String fileName) {
        try {
            if (!bucketExists(BUCKET_NAME)) {
                this.createBucket();
            }
            Map<String, String> metadata = new HashMap<>();
            metadata.put("originalFilename", file.getOriginalFilename());

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
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
    public String getImage(String fileName) {
        try {
            Map<String, String> reqParams = new HashMap<>();
            reqParams.put("response-content-type", "image/png");
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .expiry(2) // TODO: change to ".expiry(2, TimeUnit.HOURS)". reason -> https://programingqa.com/answers/intellij-idea-jdk-21-issue-with-java-util-concurrent-package-timeunit-class-n/
                            .extraQueryParams(reqParams)
                            .build());
            return url;
        } catch (Exception e) {
            return String.format("Error has occurred during get image %s", fileName);
        }
    }


    private boolean bucketExists(String bucket) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
    }

    private void createBucket() throws Exception {
        if (!this.bucketExists(BUCKET_NAME)) {
            this.minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
        }
    }
}
