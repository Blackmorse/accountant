package com.blackmorse.controller.table;

import com.blackmorse.statement.StatementModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.util.Callback;

import javax.inject.Singleton;

@Singleton
public class CellFactoryProducer {

    public Callback<TableColumn<StatementModel, String>, TableCell<StatementModel, String>> produce(final int width) {

        return new Callback<TableColumn<StatementModel, String>, TableCell<StatementModel, String>>() {
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
