package com.blackmorse.xls.reader;

import com.blackmorse.model.OperationType;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.writer.Column;
import com.blackmorse.xls.writer.WriterStrategy;
import com.blackmorse.xls.writer.income.IncomeColumns;
import com.blackmorse.xls.writer.outcome.OutcomeColumns;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class XlsReader {
    private final File file;

    public XlsReader(File file) {
        this.file = file;
    }

    public DocumentReference parseDocumentSheets() throws IOException {
        List<String> result;
        log.debug("Start parsing document {} sheets", file.getAbsolutePath());
        try (HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file))) {
            result = getSheetsFromBook(book);
        }
        log.debug("Sheet names successfully parsed: {}", result);
        return new DocumentReference(file, result);
    }

    private List<String> getSheetsFromBook(HSSFWorkbook book) {
        List<String> result = new ArrayList<>();
        Iterator<Sheet> sheetIterator = book.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            result.add(sheet.getSheetName());
        }
        return result;
    }

    public int getLastRowNumber(Sheet sheet) {
        int rowNumber = WriterStrategy.startRow;
        while (!rowIsEmpty(sheet.getRow(rowNumber))) {
            rowNumber++;
        }
        return rowNumber;
    }

    private boolean rowIsEmpty(Row row) {
        for (int i = 0; i < WriterStrategy.endColumn; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK)
                return false;
        }
        return true;
    }

    public Set<ThemeStatisticEntry> getThemesStatistics(List<String> sheets) throws IOException{
        Set<ThemeStatisticEntry> result = new HashSet<>();
        log.debug("Parsing document {} themes", file.getAbsolutePath());
        try (HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file))) {
            Iterator<Sheet> sheetIterator = book.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                if(sheets.contains(sheet.getSheetName())) {
                    result.addAll(getThemeStatisticsFromSheet(sheet));
                }
            }
        }
        log.debug("Find {} themes", result.size());
        log.trace("Themes are {}", result);
        return result;
    }

    private List<ThemeStatisticEntry> getThemeStatisticsFromSheet(Sheet sheet) {
        log.trace("Reading themes from  {} file, {} sheet", file.getAbsolutePath(), sheet.getSheetName());
        List<ThemeStatisticEntry> result = new ArrayList<>();
        int lastRow = getLastRowNumber(sheet);
        for (int i = WriterStrategy.startRow; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            ThemeStatisticEntry incomeStatisticEntry = readStatisticEntry(row, IncomeColumns.THEME, IncomeColumns.SUM, OperationType.INCOME);
            if (incomeStatisticEntry != null) {
                result.add(incomeStatisticEntry);
            }

            ThemeStatisticEntry outcomeStatisticEntry = readStatisticEntry(row, OutcomeColumns.THEME, OutcomeColumns.SUM, OperationType.OUTCOME);
            if (outcomeStatisticEntry != null) {
                result.add(outcomeStatisticEntry);
            }
        }
        return result;
    }

    private ThemeStatisticEntry readStatisticEntry(Row row, Column themeColumn, Column sumColumn, OperationType operationType) {
        Cell themeCell = row.getCell(themeColumn.getColumnNumber());
        Cell sumCell = row.getCell(sumColumn.getColumnNumber());
        if (themeCell != null && themeCell.getCellType() == CellType.STRING) {
            String theme = themeCell.getStringCellValue();
            Double sum = sumCell.getNumericCellValue();
            if (theme != null && !theme.isEmpty()) {
                return new ThemeStatisticEntry(theme, sum, operationType);
            }
        }
        return null;
    }
}
