package ru.seals.delivery.service;

import ru.seals.delivery.model.Person;

public interface PersonService {
    Person save(Person person);
    Person getAuthenticated();
    Person getByKeycloakId(String kcId);
}
