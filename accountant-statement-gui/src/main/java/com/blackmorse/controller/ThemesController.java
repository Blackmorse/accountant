package com.blackmorse.controller;

import com.blackmorse.model.StatementModel;
import com.blackmorse.statement.IThemesProvider;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.writer.XlsWriter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Future;

@Slf4j
public class ThemesController implements Initializable {

    @FXML public Button okButton;
    @FXML public TextField themesField;
    @FXML private ListView<String> listView;
    private IThemesProvider themesProvider;

    private StatementModel model;
    private String sheetName;
    private DocumentReference documentReference;

    @Inject
    public ThemesController(IThemesProvider themesProvider) {
        this.themesProvider = themesProvider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Future<Set<String>> themesFuture = themesProvider.getThemes();
        try {
            Set<String> strings = themesFuture.get();
            listView.setItems(FXCollections.observableArrayList(strings).sorted());
            listView.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> themesField.setText(newValue));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            themesFuture.cancel(true);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось подгрузить темы", ButtonType.OK);
            alert.showAndWait();
        }
    }

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
