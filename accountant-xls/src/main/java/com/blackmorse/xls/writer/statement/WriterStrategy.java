package com.blackmorse.xls.writer.statement;

import com.blackmorse.model.statement.StatementModel;
import org.apache.poi.ss.usermodel.*;

public interface WriterStrategy {
    int startRow = 11;
    int endColumn = 10;

    void writeRow(Workbook book, Row row, StatementModel model, String theme, String comment,
                  CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format);
}
