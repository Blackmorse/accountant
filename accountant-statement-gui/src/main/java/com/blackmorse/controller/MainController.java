package com.blackmorse.controller;

import com.blackmorse.controller.table.*;
import com.blackmorse.controller.table.model.StatementModel;
import com.blackmorse.guice.FXMLLoaderProvider;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class MainController implements Initializable {
    private final FXMLLoaderProvider fxmlLoaderProvider;
    private final TableWrapperFactory tableWrapperFactory;

    @FXML private TableView<StatementModel> table;
    private TableWrapper tableWrapper;


    @Inject
    public MainController(FXMLLoaderProvider fxmlLoaderProvider,
                          TableWrapperFactory tableWrapperFactory) {
        this.fxmlLoaderProvider = fxmlLoaderProvider;
        this.tableWrapperFactory = tableWrapperFactory;
    }

    @FXML
    public void openThemes(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Темы");

        Parent parent = fxmlLoaderProvider.get().load(
                MainController.class.getResourceAsStream("/fxml/themes.fxml"));
        stage.setScene(new Scene(parent));
        stage.show();
        log.info("Themes window is open");
    }

    @FXML
    public void loadStatements(ActionEvent event) throws Exception {
        tableWrapper.loadData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableWrapper = tableWrapperFactory.createTableWrapper(table);
    }
}
