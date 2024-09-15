package ru.seals.delivery.model.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.javamoney.moneta.Money;
import ru.seals.delivery.model.common.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends BaseEntity {
    private String url;
    private String description;
    private Money price;
    @Column(columnDefinition = "numeric(8, 2)")
    private Long weight;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Order order;
}