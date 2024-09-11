package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import java.util.List;

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
    public Order saveOrder(@RequestBody OrderSaveDTO order) {
        return personService.saveOrder(order);
    }

    @GetMapping("/tracking")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Получение заказа по его внутреннему номеру")
    public Order getOrderByTrackInternalNumber(@RequestParam String trackNumber) {
        return personService.getOrderByTrackInternalNumber(trackNumber);
    }
    @PostMapping(value = "changePersonPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Изменение фото профиля")
    public void changePersonPhoto(
            @RequestPart(value = "userId") Long id,
            @RequestPart(value = "file") MultipartFile file) {
        //TODO id брать из токена
        personService.changePersonPhoto(id, file); //TODO вернуть ResponseEntity.ok
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
    //TODO проверить запрос
    public Page<Order> getUserOrders(@PathVariable int page, @RequestParam int size) {
        return personService.getUserOrders(PageRequest.of(page, size));
    }

    //TODO адекватно получать даты в заказе
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

    @GetMapping("/person/address")
    @Operation(description = "Получение всех адерсов юзера по его ID")
    public List<SaveAddress> getAllPersonAddressById() {
        return personService.getAllPersonAddressById();
    }

    @DeleteMapping("/address/delete/{id}")
    @Operation(description = "Удаление заказа по ID")
    public void deleteAddressById(@PathVariable Long id) {
        personService.deleteAddressById(id);
    }
}
