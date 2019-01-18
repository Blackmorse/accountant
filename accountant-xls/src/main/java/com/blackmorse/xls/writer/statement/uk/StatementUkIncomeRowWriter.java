package com.blackmorse.xls.writer.statement.uk;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.writer.statement.StatementRowWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class StatementUkIncomeRowWriter implements StatementRowWriter {
    @Override
    public void writeRow(Workbook book, Row row, OutputStatementEntry model,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        throw new UnsupportedOperationException();
    }
}
