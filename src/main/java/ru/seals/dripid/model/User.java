package ru.seals.dripid.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import ru.seals.dripid.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(/*cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true*/)
    @JoinColumn(name = "user_id")
    private List<SaveAddresses> saveAddresses = new ArrayList<>();

    @Column(unique = true)
    private String email;

    private String password;
    private Money balance;
    private String image;
    private String firstName;
    private String lastName;
    private String phone;
}
