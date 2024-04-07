package ru.seals.delivery.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    void saveImage(MultipartFile file, String fileName, String bucketName);

    String getImage(String bucketName, String fileName);
}
