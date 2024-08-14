package ru.seals.delivery.model;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.seals.delivery.hibernate.converter.TimestampToLocalDateTimeAttributeConverter;
import ru.seals.delivery.model.enums.OrderStatus;
import ru.seals.delivery.model.enums.OrderType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
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
    @UpdateTimestamp
    @Convert(converter = TimestampToLocalDateTimeAttributeConverter.class)
    private LocalDateTime lastUpdate;

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
    private String address;

    @Column(columnDefinition = "date")
    @CreatedDate
    private Date createdDate;

    @Column(columnDefinition = "date")
    @LastModifiedDate
    private Date modifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;
}
