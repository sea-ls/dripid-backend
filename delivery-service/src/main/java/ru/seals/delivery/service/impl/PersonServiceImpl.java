package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.seals.delivery.dto.OrderPreviewDTO;
import ru.seals.delivery.dto.OrderSaveDTO;
import ru.seals.delivery.model.delivery.Order;
import ru.seals.delivery.model.delivery.Person;
import ru.seals.delivery.model.delivery.Product;
import ru.seals.delivery.model.delivery.Warehouse;
import ru.seals.delivery.model.delivery.enums.MinioBuckets;
import ru.seals.delivery.model.delivery.enums.OrderStatus;
import ru.seals.delivery.repository.PersonRepository;
import ru.seals.delivery.service.KeycloakService;
import ru.seals.delivery.service.MinioService;
import ru.seals.delivery.service.OrderService;
import ru.seals.delivery.service.PersonService;
import ru.seals.delivery.util.Converter;
import ru.seals.delivery.util.ModelHelper;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private static final String SAVE_LOG = "Сохранение объекта '%s' выполнено успешно.";
    private final MinioService minioService;
    private final KeycloakService keycloakService;
    private final PersonRepository personRepository;
    private final OrderService orderService;
    private final Converter converter;
    private final ModelMapper modelMapper;

    @Override
    public Person save(Person person) {
        return personRepository.saveAndFlush(person);
    }

    @Override
    public Person getAuthenticated() {
        return getByKeycloakId(keycloakService.getKeycloakUserId());
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
        } else {
            log.error("Возникла ошибка при изменении аватара пользователя. Пользователь с id %d не найден", id);
        }
    }

    @Override
    public String getDeliveryHistory(String trackNumber) {
        return orderService.getDeliveryHistory(trackNumber);
    }

    @Override
    public Page<OrderPreviewDTO> getUserOrders(Pageable pageable, Long id) {
        return converter.mapEntityPageIntoDtoPage(
                orderService.getUserOrders(pageable, id),
                OrderPreviewDTO.class);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrder(OrderSaveDTO orderSaveDTO) {
        Order order = modelMapper.map(orderSaveDTO, Order.class);
        order.setProducts(orderSaveDTO.getProducts().stream().map(
                object -> {
                    Product product = modelMapper.map(object, Product.class);
                    //  product.setPrice(Money.of(object.getPrice(), "RUB"));
                    product.setOrder(order);
                    return product;
                }).collect(Collectors.toList()));
        order.setPerson(getAuthenticated());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setWarehouse(ModelHelper.createObjectWithIdSafe(orderSaveDTO.getWarehouseId(), Warehouse.class));
        order.setAddress(orderSaveDTO.getAddress());

        orderService.saveOrder(order);
        log.info(String.format(SAVE_LOG, order.getId()));
    }

    @Override
    public Order getOrderByTrackInternalNumber(String trackNumber) {
        return orderService.getOrderByTrackIntervalNumber(trackNumber);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderService.getOrderById(id);
    }
}
