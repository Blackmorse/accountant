package com.blackmorse.xls.reader;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class DirectoryXlsReader {
    private final File directory;

    public DirectoryXlsReader(File directory) {
        this.directory = directory;
    }

    public Set<ThemeStatisticEntry> readThemesStatisticsFromDirectory(List<String> sheets) throws IOException {
        log.debug("Scaning directory {} for themes", directory.getAbsolutePath());
        Set<ThemeStatisticEntry> result = new HashSet<>();
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".xls"));

        for (File file: files) {
            XlsReader reader = new XlsReader(file);
            result.addAll(reader.getThemesStatistics(sheets));
        }
        return result;
    }
}
