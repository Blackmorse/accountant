package com.blackmorse.model.themes;

import com.blackmorse.model.OperationType;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ThemesStatisticsHolderFactory {

    public ThemesStatisticsHolder createStatistics(Set<ThemeStatisticEntry> entries) {
        Map<String, List<ThemeStatisticEntry>> map = entries.stream()
                .collect(Collectors.groupingBy(themeStatisticEntry -> themeStatisticEntry.getTheme().toUpperCase()));

        List<SingleThemeStatistic> result = map.entrySet().stream().map(entry -> createStatistic(entry.getValue())).collect(Collectors.toList());
        return new ThemesStatisticsHolder(result);
    }

    private SingleThemeStatistic createStatistic(List<ThemeStatisticEntry> list) {
        double incoming = 0;
        double outcoming = 0;
        for (ThemeStatisticEntry entry : list) {
            if (entry.getOperationType().equals(OperationType.INCOME)) {
                incoming += entry.getSum();
            } else {
                outcoming += entry.getSum();
            }
        }

        return new SingleThemeStatistic(list.get(0).getTheme(), incoming - outcoming, list);
    }
}
