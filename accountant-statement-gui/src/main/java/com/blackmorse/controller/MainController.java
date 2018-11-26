package com.blackmorse.controller;

import com.blackmorse.guice.FXMLLoaderProvider;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    private final FXMLLoaderProvider fxmlLoaderProvider;

    @Inject
    public MainController(FXMLLoaderProvider fxmlLoaderProvider) {
        this.fxmlLoaderProvider = fxmlLoaderProvider;
    }

    @FXML
    public void openThemes(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        stage.setTitle("Темы");

        Parent parent = fxmlLoaderProvider.get().load(
                MainController.class.getResourceAsStream("/fxml/themes.fxml"));
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
