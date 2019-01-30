package com.blackmorse.controller;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.controller.table.statement.StatementTableWrapper;
import com.blackmorse.controller.table.statement.StatementTableWrapperFactory;
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
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
public class MainController implements Initializable {
    private final StatementTableWrapperFactory tableWrapperFactory;
    private final WindowOpener windowOpener;
    private Configuration configuration;

    @FXML private TableView<StatementModel> table;
    @FXML private TextField filePathTextField;
    @FXML private AnchorPane anchorPane;
    @FXML private DatePicker datePicker;
    private StatementTableWrapper tableWrapper;

    @Inject
    public MainController(WindowOpener windowOpener,
                          StatementTableWrapperFactory tableWrapperFactory,
                          Configuration configuration) {
        this.tableWrapperFactory = tableWrapperFactory;
        this.windowOpener = windowOpener;
        this.configuration = configuration;
    }

    @FXML
    public void chooseFile() {
        try {
            chooseFileWithInitialPath(Optional.of(configuration.getInitialFile()));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный путь до папки с файлами. Проверьте конфиг", ButtonType.OK);
            alert.showAndWait();
            chooseFileWithInitialPath(Optional.empty());
        }
    }

    private void chooseFileWithInitialPath(Optional<String> initialFile) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        initialFile.ifPresent(initF -> fileChooser.setInitialDirectory(new File(initF)));

        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null ) return;
        filePathTextField.setText(file.getAbsolutePath());

        tableWrapper.setExcelFile(file);
    }

    @FXML
    public void loadStatements(ActionEvent event) {
        tableWrapper.loadData();
    }

    @FXML
    public void changeDate(ActionEvent event) {
        tableWrapper.filterData(model -> {
            LocalDate datePickerValue = datePicker.getValue();
            if (datePickerValue == null) {
                return true;
            } else {
                LocalDateTime pickerDate = datePickerValue.atStartOfDay();
                LocalDateTime modelDate = LocalDateTime.ofInstant(model.getDate().toInstant(), ZoneId.systemDefault());
                return pickerDate.equals(modelDate);
            }
        });
    }

    @FXML
    public void clearDate(ActionEvent event) {
        datePicker.setValue(null);
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
        controller.setOnOkCallBack(tableWrapper::deleteSelectedItem);
    }

    public void openThemesStatistics(ActionEvent event) {
        windowOpener.openWindow("/fxml/themesStatistics.fxml", "Темы", Modality.WINDOW_MODAL, anchorPane.getScene().getWindow());
    }

    @FunctionalInterface
    public interface ThreeConsumer<X, Y, Z> {
        void accept(X x, Y y, Z z);
    }
}
