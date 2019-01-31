package com.blackmorse.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Getter
@Slf4j
public class ExportThemesHolder {
    private final Configuration configuration;
    private List<String> currentThemes;

    @Inject
    public ExportThemesHolder(Configuration configuration) {
        this.configuration = configuration;
        if (configuration.getSettingsFile() != null) {
            try (FileInputStream fis = new FileInputStream(new File(configuration.getSettingsFile()));
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                Object o = ois.readObject();
                currentThemes = (ArrayList<String>) o;
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                currentThemes = new ArrayList<>();
            }
        } else {
            currentThemes = new ArrayList<>();
        }
    }


    public void saveThemes() {
        if (configuration.getSettingsFile() != null) {

            File file = new File(configuration.getSettingsFile());
            try {
                file.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(file);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(currentThemes);
                }
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        }
    }
}
