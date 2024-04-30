package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.controller.clients.UserAuthServiceClient;
import ru.seals.delivery.dto.OrderPreviewDTO;
import ru.seals.delivery.dto.PersonDTO;
import ru.seals.delivery.dto.UserDTO;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.service.OrderService;
import ru.seals.delivery.service.PersonService;
import ru.seals.delivery.model.Person;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/delivery-service/person")
@RequiredArgsConstructor
@CrossOrigin
public class PersonController {
    private final UserAuthServiceClient userAuthServiceClient;
    private final PersonService personService;

    @GetMapping("authenticated")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Получение аутентифицированного пользователя по его Bearer-токену")
    public PersonDTO getAuthenticated(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        UserDTO accountInfo = userAuthServiceClient.getAuthenticatedUser(bearerToken);
        Person person = personService.getAuthenticated();
        return new PersonDTO(accountInfo,
                person.getSaveAddresses(),
                person.getBalance().getNumber().numberValue(BigDecimal.class),
                person.getImage());
    }

    @PostMapping("changePersonPhoto")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Изменение фото профиля")
    public void changePersonPhoto(
            @RequestPart(value = "userId") Long id,
            @RequestPart(value = "file") MultipartFile file) {
        personService.changePersonPhoto(id, file);
    }

    @GetMapping("order/tracking")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Получение истории доставки заказа по трек-номеру")
    public String getDeliveryHistory(@RequestParam String trackNumber) {
        return personService.getDeliveryHistory(trackNumber);
    }

    @GetMapping("orders/{page}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Получение превью всех заказов юзера")
    public Page<OrderPreviewDTO> getUserOrders(@RequestParam Long id, @PathVariable int page, @RequestParam int size) {
        return personService.getUserOrders(PageRequest.of(page, size), id);
    }

    @GetMapping("/order/{id}")
    @Operation(description = "Получение заказа по ID")
    public Order getOrderById(@PathVariable Long id) {
        return personService.getOrderById(id);
    }
}
