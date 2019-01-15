package com.blackmorse.statement;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.model.themes.ThemesStatisticsHolder;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.model.themes.ThemesStatisticsHolderFactory;
import com.blackmorse.utils.FileUtils;
import com.blackmorse.xls.reader.DirectoryXlsReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Singleton
@Slf4j
public class ThemesStatisticProvider implements IThemesStatisticProvider {
    @Getter
    private final Future<ThemesStatisticsHolder> themesStatistics;

    @Inject
    public ThemesStatisticProvider(ExecutorService executor, Configuration configuration, ThemesStatisticsHolderFactory statisticsFactory) {
        themesStatistics = executor.submit(() -> {
            Set<ThemeStatisticEntry> result = new HashSet<>();
            for (String directory : configuration.getXlsDirectories()) {
                DirectoryXlsReader reader = new DirectoryXlsReader(FileUtils.getFileFromString(directory));
                result.addAll(reader.readThemesStatisticsFromDirectory(configuration.getSheetsForThemes()));
            }
            return statisticsFactory.createStatistics(result);
        });
    }
}
