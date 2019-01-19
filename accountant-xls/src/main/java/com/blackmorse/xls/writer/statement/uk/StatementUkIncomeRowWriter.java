package com.blackmorse.xls.writer.statement.uk;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.blackmorse.xls.writer.statement.StatementRowWriter;
import org.apache.poi.ss.usermodel.Row;

public class StatementUkIncomeRowWriter implements StatementRowWriter {
    @Override
    public void writeRow(WorkbookWrapper book, Row row, OutputStatementEntry model) {
        throw new UnsupportedOperationException();
    }
}
