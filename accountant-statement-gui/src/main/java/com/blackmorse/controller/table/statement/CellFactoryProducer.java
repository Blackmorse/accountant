package com.blackmorse.controller.table.statement;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class CellFactoryProducer<T> {

    public Callback<TableColumn<T, String>, TableCell<T, String>> produce(final int width) {

        return new Callback<TableColumn<T, String>, TableCell<T, String>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell() {
                    private Text text;

                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            text = new Text(item.toString());
                            text.setWrappingWidth(width);
                            setGraphic(text);
                        }
                    }
                };
            }
        };
    }
}
