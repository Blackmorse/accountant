package com.blackmorse.xls.writer.themes.utils;

import org.apache.poi.ss.usermodel.*;

import java.util.Date;

public class ThemesXlsUtils {
    private ThemesXlsUtils(){}

    public static void createStringCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }

    public static void createDateCell(Row row, int column, Date date, Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format =  workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("dd.MM.yyyy"));
        Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        if (date != null) {
            cell.setCellValue(date);
        }
    }

    public static void createDoubleCell(Row row, int column, Double dob) {
        Cell cell = row.createCell(column);
        cell.setCellValue(dob);
    }
}
