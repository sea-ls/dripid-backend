package ru.seals.delivery.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    void saveImage(MultipartFile file, String bucketName, String fileName);

    String getImage(String bucketName, String fileName);

    void deleteImage(String bucketName, String fileName);
}
