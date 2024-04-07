package ru.seals.delivery.model.enums;

import lombok.Getter;

@Getter
public enum MinioBuckets {
    WAREHOUSE_BUCKET("warehouse"),
    PERSON_BUCKET("person");

    private final String value;

    MinioBuckets(String bucketName) {
        this.value = bucketName;
    }
}
