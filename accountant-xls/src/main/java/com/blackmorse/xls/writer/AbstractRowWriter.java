package com.blackmorse.xls.writer;

import org.apache.poi.ss.usermodel.Row;

public interface AbstractRowWriter<T> {
    void writeRow(WorkbookWrapper workbook, Row row, T model);
}
