package ru.seals.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.SaveAddress;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private UserDTO accountInfo;
    private List<SaveAddress> saveAddresses = new ArrayList<>();
    private Money balance;
    private String image;
}
