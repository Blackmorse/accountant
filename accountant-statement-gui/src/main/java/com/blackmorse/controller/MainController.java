package com.blackmorse.controller;

import com.blackmorse.controller.table.TableWrapper;
import com.blackmorse.controller.table.TableWrapperFactory;
import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.utils.WindowOpener;
import com.blackmorse.xls.DocumentReference;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class MainController implements Initializable {
    private final TableWrapperFactory tableWrapperFactory;
    private final WindowOpener windowOpener;

    @FXML private TableView<StatementModel> table;
    @FXML private TextField filePathTextField;
    @FXML private AnchorPane anchorPane;
    private TableWrapper tableWrapper;


    @Inject
    public MainController(WindowOpener windowOpener,
                          TableWrapperFactory tableWrapperFactory) {
        this.tableWrapperFactory = tableWrapperFactory;
        this.windowOpener = windowOpener;
    }

    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");

        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null ) return;
        filePathTextField.setText(file.getAbsolutePath());

        tableWrapper.setExcelFile(file);
    }

    @FXML
    public void loadStatements(ActionEvent event) {
        try {
            tableWrapper.loadData();
        } catch (IOException | URISyntaxException e) {
            log.error("Error while loading statements files", e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный путь до файлов выписок", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.getOnCloseRequest().handle(null);
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableWrapper = tableWrapperFactory.createTable(table);
        tableWrapper.setContextMenuCallBack(this::openSelectThemes);
    }

    private void openSelectThemes(StatementModel model, String sheetName, DocumentReference documentReference) {
        FXMLLoader loader = windowOpener.openWindow("/fxml/themes.fxml", "Выбор темы", Modality.WINDOW_MODAL, anchorPane.getScene().getWindow());

        SelectThemesController controller = loader.getController();
        controller.setData(model, sheetName, documentReference);
    }

    public void openThemesStatistics(ActionEvent event) {
        windowOpener.openWindow("/fxml/themesStatistics.fxml", "Темы", Modality.WINDOW_MODAL, anchorPane.getScene().getWindow());
    }

    @FunctionalInterface
    public interface ThreeConsumer<X, Y, Z> {
        void accept(X x, Y y, Z z);
    }
}
