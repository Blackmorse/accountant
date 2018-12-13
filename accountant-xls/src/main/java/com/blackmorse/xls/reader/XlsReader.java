package com.blackmorse.xls.reader;

import com.blackmorse.model.OperationType;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.DocumentReference;
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
            Cell incomingCell = sheet.getRow(i).getCell(IncomeColumns.THEME.getColumnNumber());
            Cell incomingSumCell = sheet.getRow(i).getCell(IncomeColumns.SUM.getColumnNumber());
            if (incomingCell != null && incomingCell.getCellType() == CellType.STRING) {
                String incomingTheme = incomingCell.getStringCellValue();
                Double incomingSum = incomingSumCell.getNumericCellValue();
                if (incomingTheme != null && !incomingTheme.isEmpty()) {
                    ThemeStatisticEntry entry = new ThemeStatisticEntry(incomingTheme, incomingSum, OperationType.INCOME);
                    result.add(entry);
                }
            }

            Cell outcomingCell = sheet.getRow(i).getCell(OutcomeColumns.THEME.getColumnNumber());
            Cell outomingSumCell = sheet.getRow(i).getCell(OutcomeColumns.SUM.getColumnNumber());
            if (outcomingCell != null && outcomingCell.getCellType() == CellType.STRING) {
                String outcomingTheme = outcomingCell.getStringCellValue();
                Double outcomingSum = outomingSumCell.getNumericCellValue();
                if (outcomingTheme != null && !outcomingTheme.isEmpty()) {
                    ThemeStatisticEntry entry = new ThemeStatisticEntry(outcomingTheme, outcomingSum, OperationType.OUTCOME);
                    result.add(entry);
                }
            }
        }
        return result;
    }
}
