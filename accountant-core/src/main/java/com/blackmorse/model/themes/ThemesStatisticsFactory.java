package com.blackmorse.model.themes;

import com.blackmorse.model.OperationType;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ThemesStatisticsFactory {

    public AggregatedThemeStatistics createStatistics(Set<ThemeStatisticEntry> entries) {
        Map<String, List<ThemeStatisticEntry>> map = entries.stream()
                .collect(Collectors.groupingBy(ThemeStatisticEntry::getTheme));

        List<ThemeStatistic> result = map.entrySet().stream().map(entry -> createStatistic(entry.getValue())).collect(Collectors.toList());
        return new AggregatedThemeStatistics(result);
    }

    private ThemeStatistic createStatistic(List<ThemeStatisticEntry> list) {
        double incoming = 0;
        double outcoming = 0;
        for (ThemeStatisticEntry entry : list) {
            if (entry.getOperationType().equals(OperationType.INCOME)) {
                incoming += entry.getSum();
            } else {
                outcoming += entry.getSum();
            }
        }

        return new ThemeStatistic(list.get(0).getTheme(), incoming, outcoming);
    }

}
