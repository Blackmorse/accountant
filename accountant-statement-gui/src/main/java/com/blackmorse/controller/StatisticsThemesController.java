package com.blackmorse.controller;

import com.blackmorse.controller.table.themes.ThemesTableWrapper;
import com.blackmorse.controller.table.themes.ThemesTableWrapperFactory;
import com.blackmorse.model.themes.SingleThemeStatistic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class StatisticsThemesController implements Initializable {
    private final ThemesTableWrapperFactory themesTableWrapperFactory;
    private ThemesTableWrapper leftTableWrapper;
    private ThemesTableWrapper rightTableWrapper;

    @FXML private TableView<SingleThemeStatistic> leftTableView;
    @FXML private TableView<SingleThemeStatistic> rightTableView;

    @Inject
    public StatisticsThemesController(ThemesTableWrapperFactory themesTableWrapperFactory) {
        this.themesTableWrapperFactory = themesTableWrapperFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.leftTableWrapper = themesTableWrapperFactory.createWrapper(leftTableView);
        leftTableWrapper.init();

        this.rightTableWrapper = themesTableWrapperFactory.createWrapper(rightTableView);
        rightTableWrapper.init();
        rightTableWrapper.loadThemes();
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
        leftTableWrapper.pushTheme(rightTableWrapper.popSelectedTheme());
    }

    @FXML
    public void toRightButtonAction() {
        rightTableWrapper.pushTheme(leftTableWrapper.popSelectedTheme());
    }
}
