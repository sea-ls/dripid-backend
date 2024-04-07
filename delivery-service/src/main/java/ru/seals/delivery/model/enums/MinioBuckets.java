package ru.seals.delivery.model.enums;

import lombok.Getter;

@Getter
public enum MinioBuckets {
    WAREHOUSE_BUCKET("warehouse"),
    USER_BUCKET("user");

    private final String value;

    MinioBuckets(String bucketName) {
        this.value = bucketName;
    }
}
