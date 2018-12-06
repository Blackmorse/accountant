package com.blackmorse.xls.writer.utils;

import com.blackmorse.xls.writer.Column;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XlsUtils {
    public static final String FORMAT = "dd.mm.yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);

    private XlsUtils(){}

    private static CellStyle createCellStyle(HSSFWorkbook book) {
        CellStyle style = book.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        HSSFFont font = book.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial Cyr");
        style.setFont(font);

        return style;
    }

    public static void writeDateValue(HSSFWorkbook book, HSSFRow row, Column column, Date date, DataFormat format) {
        CellStyle dateStyle = createCellStyle(book);
        dateStyle.setDataFormat(format.getFormat(FORMAT));

        HSSFCell dateCell = row.getCell(column.getColumnNumber());
        dateCell.setCellStyle(dateStyle);

        dateCell.setCellValue(date);
    }

    public static void writeDoubleValue(HSSFWorkbook book, HSSFRow row, Column column, Double value, DataFormat format) {
        CellStyle sumStyle = createCellStyle(book);
        sumStyle.setDataFormat(format.getFormat("#,##0.00"));

        HSSFCell sumCell = row.createCell(column.getColumnNumber());
        sumCell.setCellStyle(sumStyle);
        sumCell.setCellType(CellType.NUMERIC);
        sumCell.setCellValue(value);
    }

    public static void writeStringValue(HSSFWorkbook book, HSSFRow row, Column column, String value) {
        CellStyle style = createCellStyle(book);

        HSSFCell firmCell = row.createCell(column.getColumnNumber());
        firmCell.setCellStyle(style);
        firmCell.setCellValue(value);
    }
}
