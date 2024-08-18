package ru.seals.delivery.model.delivery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person extends BaseEntity {
    private String keycloakId;
    @OneToMany(/*cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true*/)
    @JoinColumn(name = "person_id")
    private List<SaveAddress> saveAddresses = new ArrayList<>();
    private Money balance;
    private String image;
}
