package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.controller.clients.UserAuthServiceClient;
import ru.seals.delivery.dto.PersonDTO;
import ru.seals.delivery.dto.UserDTO;
import ru.seals.delivery.model.Person;
import ru.seals.delivery.service.PersonService;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "api/delivery-service/person")
@RequiredArgsConstructor
@CrossOrigin
public class PersonController {
    private final UserAuthServiceClient userAuthServiceClient;
    private final PersonService personService;
    @GetMapping("authenticated")
    @Operation(description = "Получение аутентифицированного пользователя по его Bearer-токену")
    public PersonDTO getAuthenticated(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        UserDTO accountInfo = userAuthServiceClient.getAuthenticatedUser(bearerToken);
        Person person = personService.getAuthenticated();
        return new PersonDTO(accountInfo,
                person.getSaveAddresses(),
                person.getBalance().getNumber().numberValue(BigDecimal.class),
                person.getImage());
    }
}
