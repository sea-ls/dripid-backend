package ru.seals.delivery.model;

import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CompositeType;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.enums.Role;

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keycloakId;
    @OneToMany(/*cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true*/)
    @JoinColumn(name = "person_id")
    private List<SaveAddress> saveAddresses = new ArrayList<>();
    @AttributeOverride(
            name = "balance",
            column = @Column(name = "balance")
    )
    @AttributeOverride(
            name = "currency",
            column = @Column(name = "currency")
    )
    @CompositeType(MonetaryAmountType.class)
    private MonetaryAmount balance;
    private String image;
}
