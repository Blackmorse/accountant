package com.blackmorse.xls.writer;

import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.blackmorse.xls.writer.income.IncomeColumns;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

    public void writeStatement(StatementModel model, String theme, //LocalDate date,
                               String sheetName) throws IOException {
        HSSFWorkbook book = null;
        try(FileInputStream fileInputStream = new FileInputStream(document.getFile())) {
            book = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = book.getSheet(sheetName);
            int lastRow = xlsReader.getLastRowNumber(sheet);
            HSSFRow row = sheet.getRow(lastRow);

            DataFormat format =  book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));

            HSSFCell dateCell = row.getCell(IncomeColumns.DATE.getColumnNumber());
            dateCell.setCellStyle(dateStyle);


//            dateCell.setCellValue(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            dateCell.setCellValue(model.getDate());

            CellStyle sumStyle = book.createCellStyle();
            sumStyle.setDataFormat(format.getFormat("#,##0.00"));

            HSSFCell sumCell = row.createCell(IncomeColumns.SUM.getColumnNumber());
            sumCell.setCellStyle(sumStyle);
            sumCell.setCellType(CellType.NUMERIC);
            sumCell.setCellValue(model.getSum());
//            book.write();
        }

        try(FileOutputStream outputStream = new FileOutputStream(document.getFile())) {
            book.write(outputStream);
            book.close();
        }
    }
}
