package com.blackmorse.controller;

import com.blackmorse.controller.table.TableWrapper;
import com.blackmorse.controller.table.TableWrapperFactory;
import com.blackmorse.guice.FXMLLoaderProvider;
import com.blackmorse.model.StatementModel;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class MainController implements Initializable {
    private final FXMLLoaderProvider fxmlLoaderProvider;
    private final TableWrapperFactory tableWrapperFactory;

    @FXML private TableView<StatementModel> table;
    @FXML private TextField filePathTextField;
    @FXML private DatePicker datePicker;
    @FXML private AnchorPane anchorPane;
    private TableWrapper tableWrapper;


    @Inject
    public MainController(FXMLLoaderProvider fxmlLoaderProvider,
                          TableWrapperFactory tableWrapperFactory) {
        this.fxmlLoaderProvider = fxmlLoaderProvider;
        this.tableWrapperFactory = tableWrapperFactory;
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
    public void loadStatements(ActionEvent event) throws Exception {
        tableWrapper.loadData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableWrapper = tableWrapperFactory.createTable(table);
        tableWrapper.setContextMenuCallBack(this::openThemes);
    }

    private void openThemes(StatementModel model, String sheetName) {
        Stage stage = new Stage();
        stage.setTitle("Выбор темы");

        FXMLLoader loader = fxmlLoaderProvider.get();
        Parent parent = null;
        try {

            parent = loader.load(
                    MainController.class.getResourceAsStream("/fxml/themes.fxml"));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return;
        }
        ThemesController controller = loader.getController();
        controller.setData(model, sheetName);

        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.initOwner(anchorPane.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);

        log.info("Themes window is open");
        stage.show();
    }
}
