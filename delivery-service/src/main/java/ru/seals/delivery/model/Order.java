package ru.seals.delivery.model;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import ru.seals.delivery.model.enums.DeliveryStageType;
import ru.seals.delivery.model.enums.OrderStatus;
import ru.seals.delivery.model.enums.OrderType;

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
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    private DeliveryStageType deliveryStageType;

    @ManyToOne
    private Person person;

    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private String deliveryHistory;

    private String trackNumberInternal;
    private String trackNumberExternal;
    private String address;
}
