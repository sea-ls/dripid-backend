package ru.seals.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seals.delivery.controller.clients.UserAuthServiceClient;
import ru.seals.delivery.dto.PersonDTO;
import ru.seals.delivery.dto.UserDTO;

@RestController
@RequestMapping(value = "api/delivery-service/person")
@RequiredArgsConstructor
public class PersonController {
    private final UserAuthServiceClient userAuthServiceClient;
    @GetMapping("authenticated")
    public PersonDTO getAuthenticated(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        UserDTO accountInfo = userAuthServiceClient.getAuthenticatedUser(bearerToken);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setAccountInfo(accountInfo);
        return personDTO;
    }
}
