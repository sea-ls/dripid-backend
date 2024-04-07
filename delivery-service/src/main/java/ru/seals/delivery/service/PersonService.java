package ru.seals.delivery.service;

import org.springframework.web.multipart.MultipartFile;

public interface PersonService {
    void changePersonPhoto(Long id, MultipartFile file);
}
