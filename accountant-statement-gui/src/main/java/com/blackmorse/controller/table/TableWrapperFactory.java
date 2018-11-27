package com.blackmorse.controller.table;

import com.blackmorse.statement.StatementModel;
import javafx.scene.control.TableView;

public interface TableWrapperFactory {
     TableWrapper createTableWrapper(TableView<StatementModel> table);
}
