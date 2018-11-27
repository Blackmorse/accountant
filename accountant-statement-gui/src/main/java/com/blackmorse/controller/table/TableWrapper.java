package com.blackmorse.controller.table;

import com.blackmorse.controller.table.model.StatementModel;
import com.blackmorse.controller.table.model.StatementModelConverter;
import com.blackmorse.statement.StatementLoader;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TableWrapper {
    private final TableView<StatementModel> tableView;
    private final StatementLoader statementLoader;
    private final StatementModelConverter converter;
    private final CellFactoryProducer cellFactoryProducer;


    @AssistedInject
    public TableWrapper(@Assisted TableView<StatementModel> tableView, StatementLoader statementLoader,
                        StatementModelConverter converter, CellFactoryProducer cellFactoryProducer) {
        this.tableView = tableView;
        this.statementLoader = statementLoader;
        this.converter = converter;
        this.cellFactoryProducer = cellFactoryProducer;
        initTable();
    }

    private void initTable() {
        TableColumn<StatementModel, String> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<StatementModel, String> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<StatementModel, String> sumColumn = new TableColumn<>("Сумма");
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

        TableColumn<StatementModel, String> payerColumn = new TableColumn<>("Плательщик");
        payerColumn.setCellValueFactory(new PropertyValueFactory<>("payer"));
        payerColumn.setPrefWidth(150);
        payerColumn.setCellFactory(cellFactoryProducer.produce(150));

        TableColumn<StatementModel, String> bankPayerColumn = new TableColumn<>("Банк Плательщика");
        bankPayerColumn.setCellValueFactory(new PropertyValueFactory<>("payerBank"));
        bankPayerColumn.setPrefWidth(200);
        bankPayerColumn.setCellFactory(cellFactoryProducer.produce(200));

        TableColumn<StatementModel, String> receiverColumn = new TableColumn<>("Получатель");
        receiverColumn.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        receiverColumn.setPrefWidth(200);
        receiverColumn.setCellFactory(cellFactoryProducer.produce(200));

        TableColumn<StatementModel, String> goalColumn = new TableColumn<>("Назначение платежа");
        goalColumn.setCellValueFactory(new PropertyValueFactory<>("paymentGoal"));
        goalColumn.setPrefWidth(300);
        goalColumn.setCellFactory(cellFactoryProducer.produce(300));

        TableColumn<StatementModel, String> typeColumn = new TableColumn<>("Тип операции");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("operationType"));

        tableView.getColumns().addAll(numberColumn, dateColumn, sumColumn, payerColumn, bankPayerColumn,
                receiverColumn, goalColumn, typeColumn);
    }

    public void loadData() throws IOException {
        List<StatementModel> statements = statementLoader.load()
                .stream().map(converter::convert).collect(Collectors.toList());
        tableView.setItems(FXCollections.observableArrayList(statements));
    }
}
