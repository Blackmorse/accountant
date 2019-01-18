package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.model.themes.ThemesStatisticsHolder;
import com.blackmorse.xls.OperationTypeMapper;
import com.blackmorse.xls.writer.themes.columns.ThemesIncomeColumns;
import com.blackmorse.xls.writer.themes.columns.ThemesOutcomeColumns;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ThemesWriter {
    private static final int HEADER_ROW = 0;
    private static final int TITLE_ROW = 1;
    private static final int FIRST_CONTENT_ROW = 2;

    private final OperationTypeMapper operationTypeMapper;

    @Inject
    public ThemesWriter(OperationTypeMapper operationTypeMapper) {
        this.operationTypeMapper = operationTypeMapper;
    }

    public void writeFile(File file, ThemesStatisticsHolder themesHolder) {
        try(Workbook workbook = new HSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(file)) {
            DataFormat format =  workbook.createDataFormat();

            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.MM.yyyy"));

            CellStyle stringStyle = workbook.createCellStyle();

            CellStyle doubleStyle = workbook.createCellStyle();
            doubleStyle.setDataFormat(format.getFormat("#,##0.00"));
            for (SingleThemeStatistic theme : themesHolder.getStatistic()) {
                Sheet sheet = workbook.createSheet(theme.getTheme().replaceAll("\\?", "\\."));

                sheet.setColumnWidth(ThemesOutcomeColumns.DATE.getColumnNumber(), 3000);
                sheet.setColumnWidth(ThemesIncomeColumns.THEME.getColumnNumber(), 3000);
                sheet.setColumnWidth(ThemesIncomeColumns.SUM.getColumnNumber(), 4000);
                sheet.setColumnWidth(ThemesIncomeColumns.COMMENT.getColumnNumber(), 5000);
                sheet.setColumnWidth(ThemesOutcomeColumns.THEME.getColumnNumber(), 3000);
                sheet.setColumnWidth(ThemesIncomeColumns.SUM.getColumnNumber(), 4000);
                sheet.setColumnWidth(ThemesOutcomeColumns.CONTRAGENT.getColumnNumber(), 10000);
                sheet.setColumnWidth(ThemesOutcomeColumns.COMMENT.getColumnNumber(), 5000);

                createHeaderRow(sheet);
                createTitleRow(sheet);
                createContent(sheet, theme, workbook, dateStyle, stringStyle, doubleStyle, format);
            }
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createContent(Sheet sheet, SingleThemeStatistic theme, Workbook workbook,
                               CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        int row = FIRST_CONTENT_ROW;

        List<ThemeStatisticEntry> themeStatisticEntries = theme.getThemeEntries().stream()
                .filter(en -> en.getDate() != null).sorted(Comparator.comparing(ThemeStatisticEntry::getDate)).collect(Collectors.toList());
        List<ThemeStatisticEntry> themeStatisticEntriesWithoutDate = theme.getThemeEntries().stream()
                .filter(en -> en.getDate() == null).collect(Collectors.toList());

        themeStatisticEntries.addAll(themeStatisticEntriesWithoutDate);

        for (ThemeStatisticEntry entry : themeStatisticEntries) {
            operationTypeMapper.getRowWriter(entry).write(entry, sheet, row, workbook, dateStyle, stringStyle, doubleStyle, format);
            row++;
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(HEADER_ROW);

        sheet.addMergedRegion(new CellRangeAddress(HEADER_ROW,HEADER_ROW,1, 3));
        createStringCell(headerRow, 1, "Приходы");

        sheet.addMergedRegion(new CellRangeAddress(HEADER_ROW, HEADER_ROW, 4, 7));
        createStringCell(headerRow, 4, "Расходы");
    }

    private void createTitleRow(Sheet sheet) {
        Row titleRow = sheet.createRow(TITLE_ROW);

        createStringCell(titleRow, 0, "Дата");
        createStringCell(titleRow, 1, "Тема");
        createStringCell(titleRow, 2, "Сумма");
        createStringCell(titleRow, 3, "Комментарий");
        createStringCell(titleRow, 4, "Тема");
        createStringCell(titleRow, 5, "Сумма");
        createStringCell(titleRow, 6, "Контрагент");
        createStringCell(titleRow, 7, "Комментарий");
    }

    private static void createStringCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }
}
