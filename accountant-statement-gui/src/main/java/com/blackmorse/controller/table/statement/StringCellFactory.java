package com.blackmorse.controller.table.statement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public class StringCellFactory<T> implements Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>> {
    private final Function<T, String> mapFunction;

    public StringCellFactory(Function<T, String> mapFunction) {
        this.mapFunction = mapFunction;
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<T, String> param) {
        return new SimpleStringProperty(mapFunction.apply(param.getValue()));
    }
}
