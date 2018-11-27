package com.blackmorse.controller;

import com.blackmorse.controller.table.ColumnMapper;
import com.blackmorse.controller.table.StatementModel;
import com.blackmorse.guice.FXMLLoaderProvider;
import com.blackmorse.statement.StatementLoader;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class MainController implements Initializable {
    private final FXMLLoaderProvider fxmlLoaderProvider;
    private final StatementLoader statementLoader;
    private final ColumnMapper columnMapper;

    @FXML private TableView<StatementModel> table;

    @Inject
    public MainController(FXMLLoaderProvider fxmlLoaderProvider, StatementLoader statementLoader,
                          ColumnMapper columnMapper) {
        this.fxmlLoaderProvider = fxmlLoaderProvider;
        this.statementLoader = statementLoader;
        this.columnMapper = columnMapper;
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<StatementModel, String> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<StatementModel, String> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<StatementModel, String> sumColumn = new TableColumn<>("Сумма");
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

        TableColumn<StatementModel, String> payerColumn = new TableColumn<>("Плательщик");
        payerColumn.setCellValueFactory(new PropertyValueFactory<>("payer"));

        TableColumn<StatementModel, String> bankPayerColumn = new TableColumn<>("Банк Плательщика");
        bankPayerColumn.setCellValueFactory(new PropertyValueFactory<>("payerBank"));

        TableColumn<StatementModel, String> receiverColumn = new TableColumn<>("Получатель");
        receiverColumn.setCellValueFactory(new PropertyValueFactory<>("receiver"));

        TableColumn<StatementModel, String> goalColumn = new TableColumn<>("Назначение платежа");
        goalColumn.setCellValueFactory(new PropertyValueFactory<>("paymentGoal"));

        TableColumn<StatementModel, String> typeColumn = new TableColumn<>("Тип операции");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("operationType"));

        table.getColumns().addAll(numberColumn, dateColumn, sumColumn, payerColumn, bankPayerColumn,
                receiverColumn, goalColumn, typeColumn);
    }
}
