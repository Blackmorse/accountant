package com.blackmorse.controller;

import com.blackmorse.controller.table.CellFactoryProducer;
import com.blackmorse.controller.table.StringCellFactory;
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
import java.util.ResourceBundle;

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

        TableColumn<SingleThemeStatistic, Number> delta =  new TableColumn<>("Дельта");
        delta.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDelta()));

        tableView.getColumns().addAll(themeColumn, delta);

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
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите файл");

        Stage stage = new Stage();
        File file = directoryChooser.showDialog(stage);
        if (file == null ) return;
        String outputFilePath = file.getAbsolutePath() + "\\export.xls";

        try {
            ThemesStatisticsHolder themesStatisticsHolder = statisticProvider.getThemesStatistics().get();

            ThemesWriter writer = new ThemesWriter(operationTypeMapper);

            writer.writeFile(new File(outputFilePath), themesStatisticsHolder);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подгрузить темы:" + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
