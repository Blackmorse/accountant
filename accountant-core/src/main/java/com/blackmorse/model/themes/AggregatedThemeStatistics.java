package com.blackmorse.model.themes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class AggregatedThemeStatistics {
    @Getter
    private List<ThemeStatistic> statistic;
}
