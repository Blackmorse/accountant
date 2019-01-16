package com.blackmorse.controller;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.model.themes.ThemesStatisticsHolder;
import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.statement.IThemesStatisticProvider;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.writer.statement.XlsWriter;
import com.blackmorse.xls.writer.statement.XlsWriterFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
public class SelectThemesController implements Initializable {

    @FXML public Button okButton;
    @FXML public TextField themesField;
    @FXML private ListView<String> listView;
    @FXML private TextField commentField;
    private final IThemesStatisticProvider themesProvider;
    private final XlsWriterFactory xlsWriterFactory;

    private StatementModel model;
    private String sheetName;
    private DocumentReference documentReference;
    private Runnable onOkCallBack;

    @Inject
    public SelectThemesController(IThemesStatisticProvider themesProvider,
                                  XlsWriterFactory xlsWriterFactory) {
        this.themesProvider = themesProvider;
        this.xlsWriterFactory = xlsWriterFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Future<ThemesStatisticsHolder> themesStatisticsFuture = themesProvider.getThemesStatistics();
        try {
            Set<String> strings = themesStatisticsFuture.get().getStatistic().stream().map(SingleThemeStatistic::getTheme).collect(Collectors.toSet());
            listView.setItems(FXCollections.observableArrayList(strings).sorted());
            listView.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> themesField.setText(newValue));

            themesField.textProperty().addListener((observable, oldValue, newValue) -> {
                Optional<String> listTheme = listView.getItems().stream()
                        .filter(theme -> theme.startsWith(themesField.getText())).findFirst();
                listTheme.ifPresent(theme -> listView.scrollTo(theme));

            });
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            themesStatisticsFuture.cancel(true);
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
        String theme = themesField.getText();
        String comment = commentField.getText();

        XlsWriter writer = xlsWriterFactory.createXlsWriter(documentReference);
        try {
            writer.writeStatement(model, theme, sheetName, comment);
            onOkCallBack.run();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка при записи файла", ButtonType.OK);
            alert.showAndWait();
        } catch (UnsupportedOperationException ex) {
            log.error(ex.getMessage(), ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Данная операция не поддерживается. Возможно," +
                    "вы пытаетесь записать в приходы УК", ButtonType.OK);
            alert.showAndWait();
        }
        ((Stage)okButton.getScene().getWindow()).close();
    }

    public void setOnOkCallBack(Runnable onOkCallBack) {
        this.onOkCallBack = onOkCallBack;
    }
}
