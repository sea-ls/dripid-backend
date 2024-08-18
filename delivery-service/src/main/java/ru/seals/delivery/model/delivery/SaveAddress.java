package ru.seals.delivery.model.delivery;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.seals.delivery.model.common.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveAddress extends BaseEntity {
    private String country;
    private String region;
    private String city;
    private String address;
}
