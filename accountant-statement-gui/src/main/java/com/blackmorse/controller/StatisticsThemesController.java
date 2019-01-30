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
import java.util.Collections;
import java.util.List;
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
        leftTableWrapper.loadThemes();

        this.rightTableWrapper = themesTableWrapperFactory.createWrapper(rightTableView);
        rightTableWrapper.init();
    }

    @FXML
    public void exportThemesAction() {
        exportThemes(Collections.emptyList());
    }

    private void exportThemes(List<String> themes) {
        leftTableWrapper.exportThemes(themes);
    }
}
