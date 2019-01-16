package com.blackmorse.xls.writer.statement;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class XlsWriter {
    private final WriterStrategyFactory writerStrategyFactory;
    private final DocumentReference document;
    private final XlsReader xlsReader;

    @AssistedInject
    public XlsWriter(@Assisted DocumentReference document, WriterStrategyFactory writerStrategyFactory) {
        this.document = Objects.requireNonNull(document);
        this.xlsReader = new XlsReader(document.getFile());
        this.writerStrategyFactory = writerStrategyFactory;
    }

    public void writeStatement(StatementModel model, String theme,
                               String sheetName, String comment) throws IOException {
        WriterStrategy strategy = writerStrategyFactory.createStrategy(model.getOperationType(), sheetName);

        log.debug("Start reading file {} for subsequent write with strategy {}",
                document.getFile().getAbsolutePath(), model.getOperationType());
        HSSFWorkbook book;
        try(FileInputStream fileInputStream = new FileInputStream(document.getFile())) {
            book = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = book.getSheet(sheetName);

            int lastRow = xlsReader.getLastRowNumber(sheet);
            HSSFRow row = sheet.getRow(lastRow);

            strategy.writeRow(book, row, model, theme, comment);
        }

        try (FileOutputStream outputStream = new FileOutputStream(document.getFile())) {
            book.write(outputStream);
            book.close();
        }
        log.debug("File successfully written");
    }
}
