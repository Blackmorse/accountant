package com.blackmorse.controller.table.themes;

import com.blackmorse.model.themes.SingleThemeStatistic;
import javafx.scene.control.TableView;

public interface ThemesTableWrapperFactory {
    ThemesTableWrapper createWrapper(TableView<SingleThemeStatistic> tableView);
}
