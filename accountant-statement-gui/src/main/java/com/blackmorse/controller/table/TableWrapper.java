package com.blackmorse.controller.table;

import com.blackmorse.controller.MainController;
import com.blackmorse.model.StatementModel;
import com.blackmorse.controller.table.model.StatementModelConverter;
import com.blackmorse.statement.StatementLoader;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.blackmorse.xls.writer.utils.XlsUtils;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
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
    private MainController.ThreeConsumer<StatementModel, String, DocumentReference> contextMenuCallBack;


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
        TableColumn<StatementModel, Number> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()));

        TableColumn<StatementModel, String> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new StatementCellFactory(model -> XlsUtils.DATE_FORMAT.format(model.getDate())));

        TableColumn<StatementModel, Number> sumColumn = new TableColumn<>("Сумма");
        sumColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSum()));

        TableColumn<StatementModel, String> payerColumn = new TableColumn<>("Плательщик");
        payerColumn.setCellValueFactory(new StatementCellFactory(StatementModel::getPayer));
        payerColumn.setPrefWidth(150);
        payerColumn.setCellFactory(cellFactoryProducer.produce(150));

        TableColumn<StatementModel, String> bankPayerColumn = new TableColumn<>("Банк Плательщика");
        bankPayerColumn.setCellValueFactory(new StatementCellFactory(StatementModel::getPayerBank));
        bankPayerColumn.setPrefWidth(200);
        bankPayerColumn.setCellFactory(cellFactoryProducer.produce(200));

        TableColumn<StatementModel, String> receiverColumn = new TableColumn<>("Получатель");
        receiverColumn.setCellValueFactory(new StatementCellFactory(StatementModel::getReceiver));
        receiverColumn.setPrefWidth(200);
        receiverColumn.setCellFactory(cellFactoryProducer.produce(200));

        TableColumn<StatementModel, String> goalColumn = new TableColumn<>("Назначение платежа");
        goalColumn.setCellValueFactory(new StatementCellFactory(StatementModel::getPaymentGoal));
        goalColumn.setPrefWidth(300);
        goalColumn.setCellFactory(cellFactoryProducer.produce(300));

        TableColumn<StatementModel, String> typeColumn = new TableColumn<>("Тип операции");
        typeColumn.setCellValueFactory(new StatementCellFactory(model -> model.getOperationType().getStringValue()));

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

    public void setContextMenuCallBack(MainController.ThreeConsumer<StatementModel, String, DocumentReference> contextMenuCallBack) {
        this.contextMenuCallBack = contextMenuCallBack;
    }

    private void addMenuItems() {
        for (String sheetName : documentReference.getSheetNames()) {
            MenuItem menuItem = new MenuItem(sheetName);
            menuItem.setOnAction(e -> {
                MenuItem o = (MenuItem) e.getSource();
                StatementModel selectedItem = tableView.getSelectionModel().getSelectedItem();
                contextMenuCallBack.accept(selectedItem, o.getText(), documentReference);
            });
            menu.getItems().add(menuItem);
        }
    }
}
