package com.blackmorse.model.themes;

import com.blackmorse.model.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeStatisticEntry {
    private String theme;
    private Double sum;
    private OperationType operationType;
}
