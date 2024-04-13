package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.model.Person;
import ru.seals.delivery.model.enums.MinioBuckets;
import ru.seals.delivery.repository.PersonRepository;
import ru.seals.delivery.service.MinioService;
import ru.seals.delivery.service.PersonService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final MinioService minioService;
    private final PersonRepository personRepository;

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
