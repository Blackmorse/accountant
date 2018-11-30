package com.blackmorse.controller;

import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.writer.XlsWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ThemesController {

    @FXML public Button okButton;
    @FXML public TextField themesField;
    private StatementModel model;
    private String sheetName;
    private DocumentReference documentReference;

    public void setData(StatementModel model, String sheetName, DocumentReference documentReference) {
        this.model = model;
        this.sheetName = sheetName;
        this.documentReference = documentReference;
    }

    @FXML
    public void okButtonAction() {
        if (themesField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Необходимо указать тему", ButtonType.OK);
            alert.showAndWait();
        }

        String theme = themesField.getText();

        XlsWriter writer = new XlsWriter(documentReference);
        try {
            writer.writeStatement(model, theme, sheetName);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка при записи файла", ButtonType.OK);
            alert.showAndWait();
        }
        ((Stage)okButton.getScene().getWindow()).close();
    }
}
