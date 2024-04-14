package ru.seals.delivery.service;

import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.model.Person;

public interface PersonService {
    Person save(Person person);
    Person getAuthenticated();
    Person getByKeycloakId(String kcId);
    void changePersonPhoto(Long id, MultipartFile file);
}
