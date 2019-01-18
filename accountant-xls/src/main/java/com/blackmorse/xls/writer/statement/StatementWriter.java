package com.blackmorse.xls.writer.statement;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class StatementWriter {
    private final WriterStrategyFactory writerStrategyFactory;
    private final DocumentReference document;
    private final XlsReader xlsReader;

    @AssistedInject
    public StatementWriter(@Assisted DocumentReference document, WriterStrategyFactory writerStrategyFactory) {
        this.document = Objects.requireNonNull(document);
        this.xlsReader = new XlsReader(document.getFile());
        this.writerStrategyFactory = writerStrategyFactory;
    }

    public void writeStatement(OutputStatementEntry outputStatementEntry, String sheetName) throws IOException {
        StatementRowWriter rowWriter = writerStrategyFactory.createStrategy(
                outputStatementEntry.getStatementModel().getOperationType(), sheetName);

        log.debug("Start reading file {} for subsequent write with strategy {}",
                document.getFile().getAbsolutePath(), outputStatementEntry.getStatementModel().getOperationType());
        HSSFWorkbook book;
        try(FileInputStream fileInputStream = new FileInputStream(document.getFile())) {
            book = new HSSFWorkbook(fileInputStream);

            DataFormat format =  book.createDataFormat();

            CellStyle dateStyle = createCellStyle(book);
            dateStyle.setDataFormat(format.getFormat("dd.MM.yyyy"));

            CellStyle stringStyle = createCellStyle(book);;

            CellStyle doubleStyle = createCellStyle(book);
            doubleStyle.setDataFormat(format.getFormat("#,##0.00"));

            HSSFSheet sheet = book.getSheet(sheetName);

            int lastRow = xlsReader.getLastRowNumber(sheet);
            Row row = sheet.createRow(lastRow);

            rowWriter.writeRow(book, row, outputStatementEntry, dateStyle, stringStyle, doubleStyle, format);
        }

        try (FileOutputStream outputStream = new FileOutputStream(document.getFile())) {
            book.write(outputStream);
            book.close();
        }
        log.debug("File successfully written");
    }

    static CellStyle createCellStyle(Workbook book) {
        CellStyle style = book.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        Font font = book.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial Cyr");
        style.setFont(font);

        return style;
    }
}
