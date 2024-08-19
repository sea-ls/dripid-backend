package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.controller.clients.UserAuthServiceClient;
import ru.seals.delivery.dto.*;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.Person;
import ru.seals.delivery.model.delivery.SaveAddress;
import ru.seals.delivery.service.PersonService;
import ru.seals.delivery.util.Converter;

@RestController
@RequestMapping(value = "/api/delivery-service/person")
@RequiredArgsConstructor
public class PersonController {
    private final UserAuthServiceClient userAuthServiceClient;
    private final PersonService personService;
    private final Converter converter;

    @GetMapping("authenticated")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Получение аутентифицированного пользователя по его Bearer-токену")
    public PersonDTO getAuthenticated(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        UserDTO accountInfo = userAuthServiceClient.getAuthenticatedUser(bearerToken);
        Person person = personService.getAuthenticated();
        return converter.mapPersonToDto(accountInfo, person);
    }

    @PostMapping("/order/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Сохранение заказа")
    public void saveOrder(@RequestBody OrderSaveDTO order) {
        personService.saveOrder(order);
    }

    @GetMapping("/tracking")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Получение заказа по его внутреннему номеру")
    public Order getOrderByTrackInternalNumber(@RequestParam String trackNumber) {
        return personService.getOrderByTrackInternalNumber(trackNumber);
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

    @PostMapping("/address/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Сохранение адреса")
    public void saveAddress(@RequestBody AddressSaveDTO address) {
        personService.saveAddress(address);
    }

    @GetMapping("/address/{id}")
    @Operation(description = "Получение адреса по ID")
    public SaveAddress getAddressById(@PathVariable Long id) {
        return personService.getAddressById(id);
    }

    @DeleteMapping("/address/delete/{id}")
    @Operation(description = "Удаление заказа по ID")
    public void deleteAddressById(@PathVariable Long id) {
        personService.deleteAddressById(id);
    }
}
