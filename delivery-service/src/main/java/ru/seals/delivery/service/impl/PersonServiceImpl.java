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
import ru.seals.delivery.dto.AddressSaveDTO;
import ru.seals.delivery.dto.OrderPreviewDTO;
import ru.seals.delivery.dto.OrderSaveDTO;
import ru.seals.delivery.model.delivery.*;
import ru.seals.delivery.model.delivery.enums.MinioBuckets;
import ru.seals.delivery.model.delivery.enums.OrderStatus;
import ru.seals.delivery.repository.PersonRepository;
import ru.seals.delivery.service.*;
import ru.seals.delivery.util.Converter;
import ru.seals.delivery.util.ModelHelper;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private static final String SAVE_LOG = "Сохранение объекта '%s' выполнено успешно.";
    private final MinioService minioService;
    private final KeycloakService keycloakService;
    private final PersonRepository personRepository;
    private final AddressService addressService;
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
        Person p = personRepository.findPersonByKeycloakId(kcId)
                .orElseGet(() -> {
                    Person person = new Person();
                    person.setKeycloakId(kcId);
                    person.setBalance(Money.of(new BigDecimal(0), Monetary.getCurrency("RUB")));
                    return save(person);
                });
        if (!p.getSaveAddresses().isEmpty()) {
            List<SaveAddress> addresses = p.getSaveAddresses();
            p.setSaveAddresses(
                    addresses.stream().filter(a -> !a.isDeleted()).toList()
            );
        }
        return p;
    }

    @Override
    public void changePersonPhoto(MultipartFile file) {
        Optional<Person> result = personRepository
                .findPersonByKeycloakId(keycloakService.getKeycloakUserId());

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
            log.error("Возникла ошибка при изменении аватара пользователя. Пользователь с kc id %s не найден",
                    keycloakService.getKeycloakUserId());
        }
    }

    @Override
    public String getDeliveryHistory(String trackNumber) {
        return orderService.getDeliveryHistory(trackNumber);
    }

    @Override
    public Page<Order> getUserOrders(Pageable pageable) {
        return orderService.getUserOrders(pageable);
    }
    @Override
    @Transactional
    public Order saveOrder(OrderSaveDTO orderSaveDTO) {
        Order order = converter.mapOrderSaveDTOtoOrder(orderSaveDTO);
        order.setPerson(getAuthenticated());

        orderService.saveOrder(order);
        log.info(String.format(SAVE_LOG, order.getId()));
        return order;
    }

    @Override
    public Order getOrderByTrackInternalNumber(String trackNumber) {
        return orderService.getOrderByTrackIntervalNumber(trackNumber);
    }

    @Override
    public void saveAddress(AddressSaveDTO address) {
        SaveAddress newAddress = modelMapper.map(address, SaveAddress.class);
        addressService.saveNewAddress(newAddress);

        Person person = getAuthenticated();
       // person.getSaveAddresses().add(newAddress);

        List<SaveAddress> mutable = new ArrayList<>(person.getSaveAddresses());
        mutable.add(newAddress);
        person.setSaveAddresses(mutable);
        personRepository.save(person);
    }

    @Override
    public void deleteAddressById(Long id) {
        addressService.deleteAddress(id);
    }

    @Override
    public SaveAddress getAddressById(Long id) {
        return addressService.getAddressById(id);
    }

    @Override
    public List<SaveAddress> getAllPersonAddressById() {
        return getAuthenticated().getSaveAddresses();
    }


    @Override
    public Order getOrderById(Long id) {
        return orderService.getOrderById(id);
    }
}
