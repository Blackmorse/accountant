package com.blackmorse.xls.writer;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public interface AbstractRowWriter<T> {
    void writeRow(Workbook workbook, Row row, T model,
                  CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format);
}
