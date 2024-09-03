package ru.seals.delivery.model.delivery;

import jakarta.persistence.Entity;
import lombok.*;
import ru.seals.delivery.model.common.BaseEntity;

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
