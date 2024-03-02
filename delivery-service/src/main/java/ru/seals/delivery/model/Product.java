package ru.seals.delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String description;
    @Column(columnDefinition = "money")
    private Money price;
    private Long weight;
}