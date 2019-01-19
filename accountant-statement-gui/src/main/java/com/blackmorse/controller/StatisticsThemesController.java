package com.blackmorse.controller;

import com.blackmorse.controller.table.CellFactoryProducer;
import com.blackmorse.controller.table.StringCellFactory;
import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.statement.ThemesStatisticProvider;
import com.blackmorse.xls.writer.themes.OperationTypeMapper;
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
        //TODO удалить

//        try {
//            ThemesStatisticsHolder themesStatisticsHolder = statisticProvider.getThemesStatistics().get();
//
//            ThemesWriter writer = new ThemesWriter(operationTypeMapper);
//
//            writer.writeFile(new File("D:\\accountant-config\\themes.xls"), themesStatisticsHolder);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}
