package com.blackmorse.controller.table;

import com.blackmorse.model.statement.StatementModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public class StatementCellFactory implements Callback<TableColumn.CellDataFeatures<StatementModel, String>, ObservableValue<String>> {
    private final Function<StatementModel, String> mapFunction;

    public StatementCellFactory(Function<StatementModel, String> mapFunction) {
        this.mapFunction = mapFunction;
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<StatementModel, String> param) {
        return new SimpleStringProperty(mapFunction.apply(param.getValue()));
    }
}
