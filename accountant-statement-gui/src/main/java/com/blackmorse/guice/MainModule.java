package com.blackmorse.guice;

import com.blackmorse.configuration.Configuration;
import com.google.inject.AbstractModule;
import javafx.fxml.FXMLLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainModule extends AbstractModule {
    private final Configuration configuration;

    public MainModule(String configPath) throws Exception{
        Configuration configuration;
        try (InputStream in = Files.newInputStream(Paths.get(configPath))){
            Yaml yaml = new Yaml();
            configuration = yaml.loadAs(in, Configuration.class);
        }
        this.configuration = configuration;
    }

    @Override
    protected void configure() {

        bind(FXMLLoader.class).toInstance(new FXMLLoader());

        bind(Configuration.class).toInstance(configuration);
    }
}
