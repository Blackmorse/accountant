package com.blackmorse.xls.writer;

import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.blackmorse.xls.writer.income.IncomeWriterStrategy;
import com.blackmorse.xls.writer.outcome.OutcomeWriterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class XlsWriter {

    private final DocumentReference document;
    private final XlsReader xlsReader;

    public XlsWriter(DocumentReference document) {
        Objects.requireNonNull(document);
        this.document = document;
        this.xlsReader = new XlsReader(document.getFile());
    }

    public void writeStatement(StatementModel model, String theme,
                               String sheetName) throws IOException {
        WriterStrategy strategy;
        if (StatementModel.OperationType.INCOME.equals(model.getOperationType())) {
            strategy = new IncomeWriterStrategy();
        } else {
            strategy = new OutcomeWriterStrategy();
        }

        log.debug("Start reading file {} for subsequent write with strategy {}",
                document.getFile().getAbsolutePath(), model.getOperationType());
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
        log.debug("File successfully written");
    }
}
