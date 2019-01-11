package com.blackmorse.controller.table;

import com.blackmorse.controller.MainController;
import com.blackmorse.controller.table.model.StatementModelConverter;
import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.statement.StatementModelProvider;
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
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class TableWrapper {
    private final TableView<StatementModel> tableView;
    private final StatementModelProvider statementModelProvider;
    private final StatementModelConverter converter;
    private final CellFactoryProducer<StatementModel> cellFactoryProducer;

    //State variable. Когда пустой, контекстное меню не появляется
    private DocumentReference documentReference;
    private ContextMenu menu = new ContextMenu();
    private MainController.ThreeConsumer<StatementModel, String, DocumentReference> contextMenuCallBack;
    private MenuItem deleteMenuItem = new MenuItem("Удалить");


    @AssistedInject
    public TableWrapper(@Assisted TableView<StatementModel> tableView, StatementModelProvider statementModelProvider,
                        StatementModelConverter converter, CellFactoryProducer<StatementModel> cellFactoryProducer) {
        this.tableView = tableView;
        this.statementModelProvider = statementModelProvider;
        this.converter = converter;
        this.cellFactoryProducer = cellFactoryProducer;
        initTable();
    }

    private void initTable() {
        TableColumn<StatementModel, Number> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()));

        TableColumn<StatementModel, String> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new StringCellFactory<>(model -> XlsUtils.DATE_FORMAT.format(model.getDate())));

        TableColumn<StatementModel, Number> sumColumn = new TableColumn<>("Сумма");
        sumColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSum()));

        TableColumn<StatementModel, String> payerColumn = new TableColumn<>("Плательщик");
        payerColumn.setCellValueFactory(new StringCellFactory<>(StatementModel::getPayer));
        payerColumn.setPrefWidth(150);
        payerColumn.setCellFactory(cellFactoryProducer.produce(150));

        TableColumn<StatementModel, String> bankPayerColumn = new TableColumn<>("Банк Плательщика");
        bankPayerColumn.setCellValueFactory(new StringCellFactory<>(StatementModel::getPayerBank));
        bankPayerColumn.setPrefWidth(200);
        bankPayerColumn.setCellFactory(cellFactoryProducer.produce(200));

        TableColumn<StatementModel, String> receiverColumn = new TableColumn<>("Получатель");
        receiverColumn.setCellValueFactory(new StringCellFactory<>(StatementModel::getReceiver));
        receiverColumn.setPrefWidth(200);
        receiverColumn.setCellFactory(cellFactoryProducer.produce(200));

        TableColumn<StatementModel, String> goalColumn = new TableColumn<>("Назначение платежа");
        goalColumn.setCellValueFactory(new StringCellFactory<>(StatementModel::getPaymentGoal));
        goalColumn.setPrefWidth(300);
        goalColumn.setCellFactory(cellFactoryProducer.produce(300));

        TableColumn<StatementModel, String> typeColumn = new TableColumn<>("Тип операции");
        typeColumn.setCellValueFactory(new StringCellFactory<>(model -> model.getOperationType().getStringValue()));

        tableView.getColumns().addAll(numberColumn, dateColumn, sumColumn, payerColumn, bankPayerColumn,
                receiverColumn, goalColumn, typeColumn);

        tableView.setRowFactory(tv -> {
            TableRow<StatementModel> row = new TableRow<>();
            row.setContextMenu(menu);
            return row ;
        });
        menu.getItems().add(deleteMenuItem);

        deleteMenuItem.setOnAction(event -> deleteSelectedItem());
    }

    public void deleteSelectedItem() {
        StatementModel item = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().removeAll(item);
        statementModelProvider.getStatementModels().remove(item);
    }

    public void loadData() {
        List<StatementModel> statementModels = statementModelProvider.getStatementModels();
        tableView.setItems(FXCollections.observableArrayList(statementModels));
    }

    public void filterData(Predicate<StatementModel> filter) {
        List<StatementModel> statementModels = statementModelProvider.getStatementModels()
                .stream().filter(filter).collect(Collectors.toList());
        tableView.getItems().clear();
        tableView.refresh(); //JAVAFX BUG ??!??!?
        tableView.setItems(FXCollections.observableArrayList(statementModels));
    }

    public void setExcelFile(File file) {
        XlsReader reader = new XlsReader(file);
        try {
            documentReference = reader.parseDocumentSheets();
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
        menu.getItems().clear();
        menu.getItems().addAll(deleteMenuItem, new SeparatorMenuItem());
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
