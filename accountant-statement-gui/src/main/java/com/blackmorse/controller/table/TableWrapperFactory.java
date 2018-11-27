package com.blackmorse.controller.table;

import com.blackmorse.controller.table.model.StatementModel;
import javafx.scene.control.TableView;

public interface TableWrapperFactory {
     TableWrapper createTableWrapper(TableView<StatementModel> table);
}
