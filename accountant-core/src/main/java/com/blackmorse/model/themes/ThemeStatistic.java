package com.blackmorse.model.themes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeStatistic {
    private String theme;
    private Double incoming;
    private Double outcoming;
}
