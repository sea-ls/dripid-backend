package ru.seals.delivery.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSaveDTO {
    private String url;
    private String description;
    private BigDecimal price;
    private Long weight;
}
