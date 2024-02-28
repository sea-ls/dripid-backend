package ru.seals.dripid.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.dripid.model.enums.DeliveryStageType;

import java.time.LocalDate;

@Entity
@Table(name = "delivery_stage")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private DeliveryStageType type;
}
