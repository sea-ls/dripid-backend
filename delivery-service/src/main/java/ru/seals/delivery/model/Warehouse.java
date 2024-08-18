package ru.seals.delivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import ru.seals.delivery.model.base.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Warehouse extends BaseEntity {
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
