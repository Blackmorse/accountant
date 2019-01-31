package com.blackmorse.controller;

import com.blackmorse.configuration.ExportThemesHolder;
import com.blackmorse.controller.table.themes.ThemesTableWrapper;
import com.blackmorse.controller.table.themes.ThemesTableWrapperFactory;
import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.statement.ThemesStatisticProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
public class StatisticsThemesController implements Initializable {
    private final ThemesTableWrapperFactory themesTableWrapperFactory;
    private final ExportThemesHolder exportThemesHolder;
    private final ThemesStatisticProvider statisticProvider;

    private ThemesTableWrapper leftTableWrapper;
    private ThemesTableWrapper rightTableWrapper;

    @FXML
    private TableView<SingleThemeStatistic> leftTableView;
    @FXML
    private TableView<SingleThemeStatistic> rightTableView;

    @Inject
    public StatisticsThemesController(ThemesTableWrapperFactory themesTableWrapperFactory,
                                      ExportThemesHolder exportThemesHolder,
                                      ThemesStatisticProvider statisticProvider) {
        this.themesTableWrapperFactory = themesTableWrapperFactory;
        this.exportThemesHolder = exportThemesHolder;
        this.statisticProvider = statisticProvider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.leftTableWrapper = themesTableWrapperFactory.createWrapper(leftTableView);
        leftTableWrapper.init();

        this.rightTableWrapper = themesTableWrapperFactory.createWrapper(rightTableView);
        rightTableWrapper.init();

        try {
            List<SingleThemeStatistic> themeStatistics = statisticProvider.getThemesStatistics().get().getStatistic();
            List<SingleThemeStatistic> leftThemes = themeStatistics.stream()
                    .filter(theme -> exportThemesHolder.getCurrentThemes().contains(theme.getTheme())).collect(Collectors.toList());

            List<SingleThemeStatistic> rightThemes = themeStatistics.stream()
                    .filter(theme -> !exportThemesHolder.getCurrentThemes().contains(theme.getTheme())).collect(Collectors.toList());

            leftTableWrapper.setThemes(leftThemes);
            rightTableWrapper.setThemes(rightThemes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось подгрузить темы", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void exportLeftThemesAction() {
        leftTableWrapper.exportTableThemes("current");
    }

    @FXML
    public void exportRightThemesAction() {
        rightTableWrapper.exportTableThemes("archive");
    }

    @FXML
    public void toLeftButtonAction() {
        SingleThemeStatistic theme = rightTableWrapper.popSelectedTheme();
        leftTableWrapper.pushTheme(theme);
        exportThemesHolder.getCurrentThemes().add(theme.getTheme());
    }

    @FXML
    public void toRightButtonAction() {
        SingleThemeStatistic theme = leftTableWrapper.popSelectedTheme();
        rightTableWrapper.pushTheme(theme);
        exportThemesHolder.getCurrentThemes().remove(theme.getTheme());
    }
}
