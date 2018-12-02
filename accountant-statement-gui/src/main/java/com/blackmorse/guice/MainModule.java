package com.blackmorse.guice;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.controller.table.TableWrapperFactory;
import com.blackmorse.statement.IThemesProvider;
import com.blackmorse.statement.ThemesProvider;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MainModule extends AbstractModule {
    private final Configuration configuration;

    public MainModule(String configPath) throws Exception{
        Configuration configuration;
        try (InputStream in = Files.newInputStream(Paths.get(configPath))){
            Yaml yaml = new Yaml();
            configuration = yaml.loadAs(in, Configuration.class);
        }
        this.configuration = configuration;
        log.info("Detected {} configuration statementPaths", configuration.getStatementPaths().size());
    }

    @Override
    protected void configure() {
        bind(FXMLLoader.class).toInstance(new FXMLLoader());
        bind(Configuration.class).toInstance(configuration);
        bind(ExecutorService.class).toInstance(Executors.newSingleThreadExecutor());
        bind(IThemesProvider.class).to(ThemesProvider.class).asEagerSingleton();

        install(new FactoryModuleBuilder().build(TableWrapperFactory.class));
    }
}
