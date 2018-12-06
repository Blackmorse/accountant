package com.blackmorse.xls.writer.uk;

import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.writer.WriterStrategy;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UkIncomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme) {
        throw new UnsupportedOperationException();
    }
}