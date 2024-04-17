package ru.seals.delivery.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.seals.delivery.dto.BalanceHistoryDTO;
import ru.seals.delivery.model.BalanceHistory;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class Convertor {
    private final ModelMapper modelMapper;

    public <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
        return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
    }

    public BalanceHistoryDTO mapBHIntoDTO(BalanceHistory bh) {
        return new BalanceHistoryDTO(bh.getOldBalance().getNumber().numberValue(BigDecimal.class),
                bh.getNewBalance().getNumber().numberValue(BigDecimal.class),
                bh.getCheque());
    }

}
