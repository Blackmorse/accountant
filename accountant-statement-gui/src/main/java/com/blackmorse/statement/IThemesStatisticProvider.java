package com.blackmorse.statement;

import com.blackmorse.model.themes.AggregatedThemeStatistics;
import java.util.concurrent.Future;

public interface IThemesStatisticProvider {
    Future<AggregatedThemeStatistics> getThemesStatistics();
}
