package com.blackmorse.controller.table.themes;

import com.blackmorse.controller.table.statement.CellFactoryProducer;
import com.blackmorse.controller.table.statement.StringCellFactory;
import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.xls.writer.themes.OperationTypeMapper;
import com.blackmorse.xls.writer.themes.ThemesWriter;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ThemesTableWrapper {
    private TableView<SingleThemeStatistic> tableView;

    private final CellFactoryProducer<SingleThemeStatistic> cellFactoryProducer;
    private final OperationTypeMapper operationTypeMapper;

    @AssistedInject
    public ThemesTableWrapper(@Assisted TableView<SingleThemeStatistic> tableView,
                              CellFactoryProducer<SingleThemeStatistic> cellFactoryProducer,
                              OperationTypeMapper operationTypeMapper) {
        this.tableView = tableView;
        this.cellFactoryProducer = cellFactoryProducer;
        this.operationTypeMapper = operationTypeMapper;
    }

    public void init() {
        TableColumn<SingleThemeStatistic, String> themeColumn = new TableColumn<>("Тема");
        themeColumn.setCellValueFactory(new StringCellFactory<>(SingleThemeStatistic::getTheme));
        themeColumn.setPrefWidth(100);
        themeColumn.setCellFactory(cellFactoryProducer.produce(150));

        DecimalFormat df = new DecimalFormat("#.##");
        TableColumn<SingleThemeStatistic, String> deltaColumn =  new TableColumn<>("Дельта");
        deltaColumn.setCellValueFactory(new StringCellFactory<>(statistic -> df.format(statistic.getDelta())));

        tableView.getColumns().addAll(themeColumn, deltaColumn);

        ContextMenu menu = new ContextMenu();

        tableView.setRowFactory(tv -> {
            TableRow<SingleThemeStatistic> row = new TableRow<>();
            row.setContextMenu(menu);
            return row;
        });

        MenuItem exportItem = new MenuItem("Экспортировать");
        menu.getItems().add(exportItem);

        exportItem.setOnAction(event -> {
            SingleThemeStatistic item = tableView.getSelectionModel().getSelectedItem();
            exportThemes(Collections.singletonList(item), item.getTheme());
        });
    }

    public void setThemes(List<SingleThemeStatistic> themes) {
        tableView.setItems(FXCollections.observableArrayList(themes));
    }

    public void pushTheme(SingleThemeStatistic theme) {
        if (theme != null) {
            tableView.getItems().add(theme);
        }
    }

    public SingleThemeStatistic popSelectedTheme() {
        SingleThemeStatistic selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            tableView.getItems().removeAll(selectedItem);
            tableView.refresh();
        }
        return selectedItem;
    }

    public void exportTableThemes(String fileName) {
        ObservableList<SingleThemeStatistic> items = tableView.getItems();
        if (!items.isEmpty()) {
            exportThemes(items, fileName);
        }
    }

    /**
     * Export all themes in case of empty list
     */
    private void exportThemes(List<SingleThemeStatistic> themes, String fileName) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите папку");

        Stage stage = new Stage();
        File file = directoryChooser.showDialog(stage);
        if (file == null ) return;
        String outputFilePath = file.getAbsolutePath() + "\\" + fileName.replaceAll("\\?", ",") +".xls";

        try {
            ThemesWriter writer = new ThemesWriter(operationTypeMapper);
            writer.writeFile(new File(outputFilePath), themes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подгрузить темы:" + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
