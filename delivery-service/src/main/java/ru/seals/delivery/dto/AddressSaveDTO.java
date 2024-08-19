package ru.seals.delivery.dto;

import lombok.Data;

@Data
public class AddressSaveDTO {
    private String country;
    private String region;
    private String city;
    private String address;
}
