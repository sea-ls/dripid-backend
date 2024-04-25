package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.model.Person;
import ru.seals.delivery.model.enums.MinioBuckets;
import ru.seals.delivery.repository.PersonRepository;
import ru.seals.delivery.service.KeycloakService;
import ru.seals.delivery.service.MinioService;
import ru.seals.delivery.service.PersonService;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final MinioService minioService;
    private final KeycloakService keycloakService;
    private final PersonRepository personRepository;

    @Override
    public Person save(Person person) {
        return personRepository.saveAndFlush(person);
    }

    @Override
    public Person getAuthenticated() {
        return getByKeycloakId(keycloakService.getKeycloakUserId());
    }

    @Override
    public Person getByKeycloakId(String kcId) {
        return personRepository.findPersonByKeycloakId(kcId)
                .orElseGet(() -> {
                    Person person = new Person();
                    person.setKeycloakId(kcId);
                    person.setBalance(Money.of(new BigDecimal(0), Monetary.getCurrency("RUB")));
                    return save(person);
                });
    }

    @Override
    public void changePersonPhoto(Long id, MultipartFile file) {
        Optional<Person> result = personRepository.findById(id);

        if (result.isPresent()) {
            Person person = result.get();
            String newFileName = String.valueOf(UUID.randomUUID());

            person.setImage(newFileName);
            personRepository.save(person);

            minioService.saveImage(
                    file,
                    newFileName,
                    MinioBuckets.PERSON_BUCKET.getValue());
        } else {
            log.error("Возникла ошибка при изменении аватара пользователя. Пользователь с id %d не найден", id);
        }
    }
}
