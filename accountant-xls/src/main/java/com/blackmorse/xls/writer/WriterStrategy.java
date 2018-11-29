package com.blackmorse.xls.writer;

import com.blackmorse.model.StatementModel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface WriterStrategy {
    int startRow = 11;
    int endColumn = 10;

    void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme);
}
