package ru.seals.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.dto.AddressSaveDTO;
import ru.seals.delivery.dto.OrderPreviewDTO;
import ru.seals.delivery.dto.OrderSaveDTO;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.Person;
import ru.seals.delivery.model.delivery.SaveAddress;

import java.util.List;

public interface PersonService {
    Person save(Person person);
    Person getAuthenticated();
    Person getByKeycloakId(String kcId);
    void changePersonPhoto(Long id, MultipartFile file);
    String getDeliveryHistory(String trackNumber);
    Page<OrderPreviewDTO> getUserOrders(Pageable pageable, Long id);
    Order getOrderById(Long id);
    void saveOrder(OrderSaveDTO orderSaveDTO);
    Order getOrderByTrackInternalNumber(String trackNumber);

    void saveAddress(AddressSaveDTO address);
    void deleteAddressById(Long id);
    SaveAddress getAddressById(Long id);

    List<SaveAddress> getAllPersonAddressById();
}
