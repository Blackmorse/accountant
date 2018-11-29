package com.blackmorse.xls.writer;

import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.blackmorse.xls.writer.income.IncomeWriterStrategy;
import com.blackmorse.xls.writer.outcome.OutcomeWriterStrategy;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class XlsWriter {
    public enum Type {
        INCOME,
        OUTCOME
    }

    private final DocumentReference document;
    private final XlsReader xlsReader;
    private final WriterStrategy strategy;

    public XlsWriter(DocumentReference document, Type writerType) {
        Objects.requireNonNull(writerType);
        Objects.requireNonNull(document);
        this.document = document;
        this.xlsReader = new XlsReader(document.getFile());
        if (Type.INCOME.equals(writerType)) {
            this.strategy = new IncomeWriterStrategy();
        } else {
            this.strategy = new OutcomeWriterStrategy();
        }
    }

    public void writeStatement(StatementModel model, String theme,
                               String sheetName) throws IOException {
        HSSFWorkbook book;
        try(FileInputStream fileInputStream = new FileInputStream(document.getFile())) {
            book = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = book.getSheet(sheetName);

            int lastRow = xlsReader.getLastRowNumber(sheet);
            HSSFRow row = sheet.getRow(lastRow);

            strategy.writeRow(book, row, model, theme);
        }

        try (FileOutputStream outputStream = new FileOutputStream(document.getFile())) {
            book.write(outputStream);
            book.close();
        }
    }
}
