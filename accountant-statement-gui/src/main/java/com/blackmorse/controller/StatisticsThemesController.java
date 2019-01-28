package com.blackmorse.controller;

import com.blackmorse.controller.table.CellFactoryProducer;
import com.blackmorse.controller.table.StringCellFactory;
import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.model.themes.ThemesStatisticsHolder;
import com.blackmorse.statement.ThemesStatisticProvider;
import com.blackmorse.xls.writer.themes.OperationTypeMapper;
import com.blackmorse.xls.writer.themes.ThemesWriter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
public class StatisticsThemesController implements Initializable {

    private final ThemesStatisticProvider statisticProvider;
    private final CellFactoryProducer<SingleThemeStatistic> cellFactoryProducer;
    private final OperationTypeMapper operationTypeMapper;

    @FXML private TableView<SingleThemeStatistic> tableView;

    @Inject
    public StatisticsThemesController(ThemesStatisticProvider statisticProvider,
                                      CellFactoryProducer<SingleThemeStatistic> cellFactoryProducer,
                                      OperationTypeMapper operationTypeMapper) {
        this.statisticProvider = statisticProvider;
        this.cellFactoryProducer = cellFactoryProducer;
        this.operationTypeMapper = operationTypeMapper;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            exportThemes(Collections.singletonList(item.getTheme()));
        });

        try {
            tableView.setItems(FXCollections.observableArrayList(statisticProvider.getThemesStatistics().get().getStatistic()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подгрузить темы", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void exportThemesAction() {
        exportThemes(Collections.emptyList());
    }

    /**
     * Export all themes in case of empty list
     */
    private void exportThemes(List<String> themes) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите файл");

        Stage stage = new Stage();
        File file = directoryChooser.showDialog(stage);
        if (file == null ) return;
        String outputFilePath = file.getAbsolutePath() + "\\export.xls";

        try {
            ThemesStatisticsHolder themesStatisticsHolder = statisticProvider.getThemesStatistics().get();

            ThemesWriter writer = new ThemesWriter(operationTypeMapper);

            if (themes.isEmpty()) {
                themes = themesStatisticsHolder.getStatistic().stream().map(SingleThemeStatistic::getTheme)
                        .collect(Collectors.toList());
            }
            writer.writeFile(new File(outputFilePath), themesStatisticsHolder, themes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подгрузить темы:" + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
