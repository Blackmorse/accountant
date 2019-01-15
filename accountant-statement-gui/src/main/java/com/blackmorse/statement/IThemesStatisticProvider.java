package com.blackmorse.statement;

import com.blackmorse.model.themes.ThemesStatisticsHolder;
import java.util.concurrent.Future;

public interface IThemesStatisticProvider {
    Future<ThemesStatisticsHolder> getThemesStatistics();
}
