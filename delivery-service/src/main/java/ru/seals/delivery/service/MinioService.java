package ru.seals.delivery.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String saveImage(MultipartFile file);
    String getImage(String fileName);
}
