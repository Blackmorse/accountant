package com.blackmorse.xls.writer.utils;

import com.blackmorse.xls.writer.Column;
import com.blackmorse.xls.writer.WorkbookWrapper;
import org.apache.poi.ss.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XlsUtils {
    public static final String FORMAT = "dd.MM.yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);

    private XlsUtils() {}

    public static void writeDateValue(Row row, Column column, Date date, WorkbookWrapper workbook) {
        Cell dateCell = row.createCell(column.getColumnNumber());
        dateCell.setCellStyle(workbook.getDateStyle());

        if (date != null) {
            dateCell.setCellValue(date);
        }
    }

    public static void writeDoubleValue(Row row, Column column, Double value, WorkbookWrapper workbook) {

        Cell sumCell = row.createCell(column.getColumnNumber());
        sumCell.setCellStyle(workbook.getDoubleStyle());
        sumCell.setCellType(CellType.NUMERIC);
        sumCell.setCellValue(value);
    }

    public static void writeStringValue(Row row, Column column, String value, WorkbookWrapper workbook) {
        Cell firmCell = row.createCell(column.getColumnNumber());
        firmCell.setCellStyle(workbook.getStringStyle());
        firmCell.setCellValue(value);
    }
}
