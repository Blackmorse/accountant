package com.blackmorse.configuration;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class Configuration {
    private List<String> statementPaths;
    private List<String> firms;
    private List<String> xlsDirectories;
    private List<String> sheetsForThemes;
    private String initialFile;
}
