package com.blackmorse.model.themes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SingleThemeStatistic {
    private String theme;
    private Double delta;
    private Double income;
    private Double outcome;

    private List<ThemeStatisticEntry> themeEntries;
}
