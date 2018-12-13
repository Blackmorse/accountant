package com.blackmorse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OperationType {
    INCOME("приход"),
    OUTCOME("расход");

    @Getter
    private final String stringValue;
}
