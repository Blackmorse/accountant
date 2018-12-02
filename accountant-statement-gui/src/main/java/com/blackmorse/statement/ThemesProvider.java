package com.blackmorse.statement;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.xls.reader.DirectoryXlsReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Singleton
@Slf4j
public class ThemesProvider implements IThemesProvider {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Getter
    private final Future<Set<String>> themes;

    @Inject
    public ThemesProvider(Configuration configuration) {
        themes = executor.submit(() -> {
            Set<String> result = new HashSet<>();
            try {
                for (String directory : configuration.getXlsDirectories()) {
                    DirectoryXlsReader reader = new DirectoryXlsReader(new File(directory));
                    result.addAll(reader.readThemesFromDirectory());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new HashSet<>();
            }
            return result;
        });
    }
}
