package com.blackmorse;

import com.blackmorse.guice.MainModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
public class Runner extends Application {
    private static String[] arguments;

    public static void main(String[] args) {
        log.info("Starting app");
        arguments = args;
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Injector injector;
        try {
            if (arguments.length == 0) {
                throw new Exception("Не указан файл конфигурации");
            }
            injector = Guice.createInjector(new MainModule(arguments[0]));
        } catch (Exception e) {
            log.error("Error while loading configuration", e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не указан файл конфигурации", ButtonType.OK);
            alert.showAndWait();
            throw new Exception(e.getCause());
        }

        log.info("Configuration successfully loaded");
        FXMLLoader loader = injector.getInstance(FXMLLoader.class);
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load(getClass().getResourceAsStream("/fxml/main.fxml"));
        stage.setTitle("Бухгалтерия");
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Application started");
        stage.setOnCloseRequest(event -> injector.getInstance(ExecutorService.class).shutdown());
    }
}
