package ru.seals.delivery.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.seals.delivery.dto.BalanceHistoryDTO;
import ru.seals.delivery.dto.PersonDTO;
import ru.seals.delivery.dto.UserDTO;
import ru.seals.delivery.model.BalanceHistory;
import ru.seals.delivery.model.Person;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
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
        return new PersonDTO(accInfo,
                person.getSaveAddresses(),
                person.getBalance().getNumber().numberValue(BigDecimal.class),
                person.getImage());
    }
}
