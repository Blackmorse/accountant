package com.blackmorse.xls.writer.utils;

import com.blackmorse.xls.writer.Column;
import org.apache.poi.ss.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XlsUtils {
    public static final String FORMAT = "dd.MM.yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);

    private XlsUtils() {}

    public static void writeDateValue(Workbook book, Row row, Column column, Date date, DataFormat format,
                                      CellStyle dateStyle) {
//        CellStyle dateStyle = book.createCellStyle();
//        cellStyler.accept(dateStyle);
        dateStyle.setDataFormat(format.getFormat(FORMAT));

        Cell dateCell = row.createCell(column.getColumnNumber());
        dateCell.setCellStyle(dateStyle);

        if (date != null) {
            dateCell.setCellValue(date);
        }
    }

    public static void writeDoubleValue(Workbook book, Row row, Column column, Double value, DataFormat format,
                                        CellStyle sumStyle) {
//        CellStyle sumStyle = book.createCellStyle();
//        cellStyler.accept(sumStyle);
        sumStyle.setDataFormat(format.getFormat("#,##0.00"));

        Cell sumCell = row.createCell(column.getColumnNumber());
        sumCell.setCellStyle(sumStyle);
        sumCell.setCellType(CellType.NUMERIC);
        sumCell.setCellValue(value);
    }

    public static void writeStringValue(Workbook book, Row row, Column column, String value,
                                        CellStyle cellStyle) {
//        CellStyle style = book.createCellStyle();
//        cellStyler.accept(style);

        Cell firmCell = row.createCell(column.getColumnNumber());
        firmCell.setCellStyle(cellStyle);
        firmCell.setCellValue(value);
    }
}
