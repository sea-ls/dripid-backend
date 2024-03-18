package ru.seals.delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.enums.Role;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(/*cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true*/)
    @JoinColumn(name = "person_id")
    private List<SaveAddress> saveAddresses = new ArrayList<>();

    @Column(unique = true)
    private String email;

    private String password;
    @Column(columnDefinition = "money")
    private Money balance;
    private String image;
    private String firstName;
    private String lastName;
    private String phone;
}
