package com.blackmorse.xls.writer.statement;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.reader.XlsReader;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

        try(FileInputStream fileInputStream = new FileInputStream(document.getFile());
            WorkbookWrapper workbook = new WorkbookWrapper(new HSSFWorkbook(fileInputStream), StatementWriter::customizeCellStyle)) {

            Sheet sheet = workbook.getWorkbook().getSheet(sheetName);

            int lastRow = xlsReader.getLastRowNumber(sheet);
            Row row = sheet.createRow(lastRow);

            rowWriter.writeRow(workbook, row, outputStatementEntry);

            try (FileOutputStream outputStream = new FileOutputStream(document.getFile())) {
                workbook.getWorkbook().write(outputStream);
            }
        }

        log.debug("File successfully written");
    }

    private static CellStyle customizeCellStyle(CellStyle style, Workbook workbook) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        Font font = workbook.findFont(false, (short)32767, (short)200, "Arial Cyr", false, false, (short)0, (byte)0);
        if (font == null) {
            font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Arial Cyr");
        }
        style.setFont(font);
        return style;
    }
}
