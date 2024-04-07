package ru.seals.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.controller.clients.UserAuthServiceClient;
import ru.seals.delivery.dto.PersonDTO;
import ru.seals.delivery.dto.UserDTO;
import ru.seals.delivery.service.PersonService;

@RestController
@RequestMapping(value = "api/delivery-service/person")
@RequiredArgsConstructor
public class PersonController {
    private final UserAuthServiceClient userAuthServiceClient;
    private final PersonService personService;

    @GetMapping("authenticated")
    public PersonDTO getAuthenticated(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        UserDTO accountInfo = userAuthServiceClient.getAuthenticatedUser(bearerToken);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setAccountInfo(accountInfo);
        return personDTO;
    }

    @PostMapping("changePersonPhoto")
    public void changePersonPhoto(
            @RequestPart(value = "userId") Long id,
            @RequestPart(value = "file") MultipartFile file) {
        personService.changePersonPhoto(id, file);
    }
}
