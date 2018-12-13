package com.blackmorse.controller;

import com.blackmorse.controller.table.CellFactoryProducer;
import com.blackmorse.controller.table.StringCellFactory;
import com.blackmorse.model.themes.ThemeStatistic;
import com.blackmorse.statement.ThemesStatisticProvider;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class StatisticsThemesController implements Initializable {

    private final ThemesStatisticProvider statisticProvider;
    private final CellFactoryProducer<ThemeStatistic> cellFactoryProducer;

    @FXML private TableView<ThemeStatistic> tableView;

    @Inject
    public StatisticsThemesController(ThemesStatisticProvider statisticProvider,
                                      CellFactoryProducer<ThemeStatistic> cellFactoryProducer) {
        this.statisticProvider = statisticProvider;
        this.cellFactoryProducer = cellFactoryProducer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<ThemeStatistic, String> themeColumn = new TableColumn<>("Тема");
        themeColumn.setCellValueFactory(new StringCellFactory<>(ThemeStatistic::getTheme));
        themeColumn.setPrefWidth(100);
        themeColumn.setCellFactory(cellFactoryProducer.produce(150));

        TableColumn<ThemeStatistic, Number> incomingColumn =  new TableColumn<>("Приход");
        incomingColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getIncoming()));

        TableColumn<ThemeStatistic, Number> outcomingColumn =  new TableColumn<>("Расход");
        outcomingColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getOutcoming()));

        tableView.getColumns().addAll(themeColumn, incomingColumn, outcomingColumn);

        try {
            tableView.setItems(FXCollections.observableArrayList(statisticProvider.getThemesStatistics().get().getStatistic()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подгрузить темы", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
