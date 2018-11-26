package com.blackmorse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    public void openThemes(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        stage.setTitle("Темы");
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/themes.fxml"));
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
