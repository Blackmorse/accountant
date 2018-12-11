package com.blackmorse.statement;

import com.blackmorse.configuration.Configuration;
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
public class ThemesProvider implements IThemesProvider {
    @Getter
    private final Future<Set<String>> themes;

    @Inject
    public ThemesProvider(ExecutorService executor, Configuration configuration) {
        themes = executor.submit(() -> {
            Set<String> result = new HashSet<>();
            for (String directory : configuration.getXlsDirectories()) {
                DirectoryXlsReader reader = new DirectoryXlsReader(FileUtils.getFileFromString(directory));
                result.addAll(reader.readThemesFromDirectory(configuration.getSheetsForThemes()));
            }
            return result;
        });
    }
}
