package com.blackmorse;

import com.blackmorse.configuration.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Runner extends Application {
    private static String[] arguments;

    public static void main( String[] args ) {
        arguments = args;
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Configuration configuration = null;
        try (InputStream in = Files.newInputStream(Paths.get(arguments[0]))){
            Yaml yaml = new Yaml();
            configuration = yaml.loadAs(in, Configuration.class);
        } catch (Exception e) {
            System.out.println("Error while loading configuration");
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не указан файл конфигурации", ButtonType.OK);
            alert.showAndWait();
            throw new Exception();
        }

        String fxmlFile = "/fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("Бухгалтерия");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
