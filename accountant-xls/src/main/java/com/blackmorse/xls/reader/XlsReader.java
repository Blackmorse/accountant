package com.blackmorse.xls.reader;

import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.writer.WriterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class XlsReader {
    private final File file;

    public XlsReader(File file) {
        this.file = file;
    }

    public DocumentReference parseDocumentThemes() throws IOException {
        List<String> result = new ArrayList<>();
        log.debug("Start parsing document {} themes", file.getAbsolutePath());
        try (HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file))) {
            Iterator<Sheet> sheetIterator = book.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                result.add(sheet.getSheetName());
            }
        }
        log.debug("Themes successfully parsed: {}", result);
        return new DocumentReference(file, result);
    }

    public int getLastRowNumber(HSSFSheet sheet) {
        int rowNumber = WriterStrategy.startRow;
        while (!rowIsEmpty(sheet.getRow(rowNumber))) {
            rowNumber++;
        }
        return rowNumber;
    }

    private boolean rowIsEmpty(HSSFRow row) {
        for (int i = 0; i < WriterStrategy.endColumn; i++) {
            HSSFCell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK)
                return false;
        }
        return true;
    }
}
