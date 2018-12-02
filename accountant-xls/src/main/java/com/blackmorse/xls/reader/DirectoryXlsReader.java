package com.blackmorse.xls.reader;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class DirectoryXlsReader {
    private final File directory;

    public DirectoryXlsReader(File directory) {
        this.directory = directory;
    }

    public Set<String> readThemesFromDirectory() throws IOException {
        log.debug("Scaning directory {} for themes", directory.getAbsolutePath());
        Set<String> result = new HashSet<>();
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".xls"));

        for (File file: files) {
            XlsReader reader = new XlsReader(file);
            result.addAll(reader.getThemes());
        }
        return result;
    }
}
