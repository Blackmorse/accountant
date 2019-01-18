package com.blackmorse.xls.writer.statement.uk;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class UkIncomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(Workbook book, Row row, StatementModel model, String theme, String comment,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        throw new UnsupportedOperationException();
    }
}
