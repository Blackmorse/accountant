package com.blackmorse.controller.table;

import com.blackmorse.model.statement.StatementModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.util.Callback;

import javax.inject.Singleton;

//@Singleton
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
