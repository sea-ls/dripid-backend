package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.model.Person;
import ru.seals.delivery.repository.PersonRepository;
import ru.seals.delivery.service.MinioService;
import ru.seals.delivery.service.PersonService;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final MinioService minioService;
    private final PersonRepository personRepository;

    private String getKeycloakUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Person save(Person person) {
        return personRepository.saveAndFlush(person);
    }

    @Override
    public Person getAuthenticated() {
        return getByKeycloakId(getKeycloakUserId());
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
        }
    }
}
