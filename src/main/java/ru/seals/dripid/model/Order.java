package ru.seals.dripid.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.dripid.model.enums.DeliveryStageType;
import ru.seals.dripid.model.enums.OrderStatus;
import ru.seals.dripid.model.enums.OrderType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(/*cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true*/)
    @JoinColumn(name = "order_id")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    private DeliveryStageType deliveryStageType;

    @ManyToOne
    private User user;

    private String trackNumberInternal;
    private String trackNumberExternal;
    private String address;
}
