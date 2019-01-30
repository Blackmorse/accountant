package com.blackmorse.controller.table.statement;

import com.blackmorse.model.statement.StatementModel;
import javafx.scene.control.TableView;

public interface StatementTableWrapperFactory {
     StatementTableWrapper createTable(TableView<StatementModel> table);
}
