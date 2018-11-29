package com.blackmorse.xls.writer;

import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.blackmorse.xls.writer.income.IncomeColumns;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XlsWriter {
    private final DocumentReference document;
    private final XlsReader xlsReader;

    public XlsWriter(DocumentReference document) {
        this.document = document;
        xlsReader = new XlsReader(document.getFile());
    }

    public void writeStatement(StatementModel model, String theme,
                               String sheetName) throws IOException {
        HSSFWorkbook book;
        try(FileInputStream fileInputStream = new FileInputStream(document.getFile())) {
            book = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = book.getSheet(sheetName);

            int lastRow = xlsReader.getLastRowNumber(sheet);
            HSSFRow row = sheet.getRow(lastRow);

            DataFormat format =  book.createDataFormat();

            //Date
            CellStyle dateStyle = createCellStyle(book);
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));

            HSSFCell dateCell = row.getCell(IncomeColumns.DATE.getColumnNumber());
            dateCell.setCellStyle(dateStyle);

            dateCell.setCellValue(model.getDate());

            //Sum
            CellStyle sumStyle = createCellStyle(book);
            sumStyle.setDataFormat(format.getFormat("#,##0.00"));

            HSSFCell sumCell = row.createCell(IncomeColumns.SUM.getColumnNumber());
            sumCell.setCellStyle(sumStyle);
            sumCell.setCellType(CellType.NUMERIC);
            sumCell.setCellValue(model.getSum());

            //Sum
            CellStyle firmStyle = createCellStyle(book);

            HSSFCell firmCell = row.createCell(IncomeColumns.FIRM.getColumnNumber());
            firmCell.setCellStyle(firmStyle);
            firmCell.setCellValue(model.getPayer());

            //Theme
            CellStyle themeStyle = createCellStyle(book);

            HSSFCell themeCell = row.createCell(IncomeColumns.THEME.getColumnNumber());
            themeCell.setCellStyle(themeStyle);
            themeCell.setCellValue(theme);
        }

        try(FileOutputStream outputStream = new FileOutputStream(document.getFile())) {
            book.write(outputStream);
            book.close();
        }
    }

    private static CellStyle createCellStyle(HSSFWorkbook book) {
        CellStyle style = book.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
}
