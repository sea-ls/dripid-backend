package ru.seals.delivery.model.delivery;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import ru.seals.delivery.model.common.Auditable;
import ru.seals.delivery.model.delivery.enums.OrderStatus;
import ru.seals.delivery.model.delivery.enums.OrderType;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order extends Auditable {
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Person person;

    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private String deliveryHistory;

    private String trackNumberInternal;
    private String trackNumberExternal;
    @ManyToOne
    private SaveAddress address;
}
