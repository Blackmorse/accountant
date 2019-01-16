package com.blackmorse.xls.writer.statement.uk;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UkIncomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme, String comment) {
        throw new UnsupportedOperationException();
    }
}
