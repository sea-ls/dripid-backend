package ru.seals.delivery.dto;

import lombok.Data;

@Data
public class WarehouseSaveDTO {
    private String code;
    private String zipCode;
    private String country;
    private String region;
    private String city;
    private String address;
    private String phoneNumber;
    private String email;
    private String workSchedule;
    private String image;
}
