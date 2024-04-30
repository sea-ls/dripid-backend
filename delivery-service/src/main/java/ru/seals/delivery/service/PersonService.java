package ru.seals.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.dto.OrderPreviewDTO;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.Person;

public interface PersonService {
    Person save(Person person);
    Person getAuthenticated();
    Person getByKeycloakId(String kcId);
    void changePersonPhoto(Long id, MultipartFile file);

    String getDeliveryHistory(String trackNumber);

    Page<OrderPreviewDTO> getUserOrders(Pageable pageable, Long id);

    Order getOrderById(Long id);
}
