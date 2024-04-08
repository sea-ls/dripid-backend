package ru.seals.delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
            name = "amount",
            column = @Column(name = "balance")
    )
    private MonetaryAmount balance;
    private String image;
}
