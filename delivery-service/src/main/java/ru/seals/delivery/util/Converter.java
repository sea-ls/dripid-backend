package ru.seals.delivery.util;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.seals.delivery.dto.BalanceHistoryDTO;
import ru.seals.delivery.dto.OrderSaveDTO;
import ru.seals.delivery.dto.PersonDTO;
import ru.seals.delivery.dto.UserDTO;
import ru.seals.delivery.model.delivery.*;
import ru.seals.delivery.model.delivery.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class Converter {
    private final ModelMapper modelMapper;

    public <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
        return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
    }

    public <D, T> List<D> mapListDTOIntoEntity(List<T> list, Class<D> entityClass) {
        return list.stream().map(objectDto -> modelMapper.map(list, entityClass)).collect(Collectors.toList());
    }

    public BalanceHistoryDTO mapBHIntoDTO(BalanceHistory bh) {
        return new BalanceHistoryDTO(bh.getOldBalance().getNumber().numberValue(BigDecimal.class),
                bh.getNewBalance().getNumber().numberValue(BigDecimal.class),
                bh.getCheque());
    }
    public PersonDTO mapPersonToDto(UserDTO accInfo, Person person) {
        return new PersonDTO(person.getId(),
                person.getKeycloakId(),
                accInfo,
                person.getSaveAddresses(),
                person.getBalance().getNumber().numberValue(BigDecimal.class),
                person.getImage());
    }

    public Order mapOrderSaveDTOtoOrder(OrderSaveDTO orderSaveDTO) {
        Order order = new Order();

        order.setProducts(orderSaveDTO.getProducts().stream().map(
                object -> {
                    Product product = modelMapper.map(object, Product.class);
                    product.setPrice(Money.of(object.getPrice(), "RUB"));
                    product.setOrder(order);
                    return product;
                }).collect(Collectors.toList()));
        order.setOrderType(orderSaveDTO.getOrderType());
        order.setTrackNumberExternal(orderSaveDTO.getTrackNumberExternal());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setWarehouse(ModelHelper.createObjectWithIdSafe(orderSaveDTO.getWarehouseId(), Warehouse.class));
        order.setAddress(ModelHelper.createObjectWithIdSafe(orderSaveDTO.getAddressId(), SaveAddress.class));

        return order;
    }
}
