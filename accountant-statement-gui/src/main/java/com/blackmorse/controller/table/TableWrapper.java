package com.blackmorse.controller.table;

import com.blackmorse.model.StatementModel;
import com.blackmorse.model.StatementModelConverter;
import com.blackmorse.statement.StatementLoader;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TableWrapper {
    private final TableView<StatementModel> tableView;
    private final StatementLoader statementLoader;
    private final StatementModelConverter converter;
    private final CellFactoryProducer cellFactoryProducer;

    //State variable. Когда пустой, контекстное меню не появляется
    private DocumentReference documentReference;
    private ContextMenu menu = new ContextMenu();

    private Callback<TableColumn<StatementModel, Date>, TableCell<StatementModel, Date>> dateFactory = (tableColumn) -> new TableCell<StatementModel, Date>() {
        @Override
        protected void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setGraphic(new Label(StatementModelConverter.FORMAT.format(item)));
            }
        }
    };


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
        TableColumn<StatementModel, Integer> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<StatementModel, Date> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(dateFactory);

        TableColumn<StatementModel, Double> sumColumn = new TableColumn<>("Сумма");
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

        tableView.setRowFactory(tv -> {
            TableRow<StatementModel> row = new TableRow<>();
            row.setContextMenu(menu);
            return row ;
        });

    }

    public void loadData() throws IOException {
        List<StatementModel> statements = statementLoader.load()
                .stream().map(converter::convert).collect(Collectors.toList());
        tableView.setItems(FXCollections.observableArrayList(statements));
    }

    public void setExcelFile(File file) {
        XlsReader reader = new XlsReader(file);
        try {
            documentReference = reader.parseDocument();
            addMenuItems();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выбран неверный файл", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void addMenuItems() {
        for (String sheetName : documentReference.getSheetNames()) {
            MenuItem menuItem = new MenuItem(sheetName);
            menuItem.setOnAction(e -> {
                MenuItem o = (MenuItem) e.getSource();
                StatementModel selectedItem = tableView.getSelectionModel().getSelectedItem();
                System.out.println(o.getText()+ " , " + selectedItem.getPayer());
            });
            menu.getItems().add(menuItem);
        }
    }
}
