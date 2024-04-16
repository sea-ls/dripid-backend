package ru.seals.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.delivery.model.SaveAddress;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private UserDTO accountInfo;
    private List<SaveAddress> saveAddresses = new ArrayList<>();
    private BigDecimal balance;
    private String image;
}