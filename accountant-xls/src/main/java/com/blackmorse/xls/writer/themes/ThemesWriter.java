package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.model.themes.ThemesStatisticsHolder;
import com.blackmorse.xls.writer.Column;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.blackmorse.xls.writer.themes.columns.DeltaColumns;
import com.blackmorse.xls.writer.themes.columns.ThemesIncomeColumns;
import com.blackmorse.xls.writer.themes.columns.ThemesOutcomeColumns;
import com.blackmorse.xls.writer.utils.XlsUtils;
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

    public void writeFile(File file, ThemesStatisticsHolder themesHolder) throws IOException {
        try(WorkbookWrapper workbookWrapper = new WorkbookWrapper(new HSSFWorkbook(), (style, book) -> {});
            FileOutputStream fos = new FileOutputStream(file)) {
            for (SingleThemeStatistic theme : themesHolder.getStatistic()) {
                Sheet sheet = workbookWrapper.getWorkbook().createSheet(
                        theme.getTheme().replaceAll("\\?", "\\."));

                configureWidths(sheet);
                createHeaderRow(sheet, theme, workbookWrapper);
                createTitleRow(sheet, workbookWrapper);
                int row = createContent(sheet, theme, workbookWrapper);
                createFooterRow(sheet, theme, workbookWrapper, row);
            }
            workbookWrapper.getWorkbook().write(fos);
        }
    }

    private void configureWidths(Sheet sheet) {
        setWidths(ThemesIncomeColumns.values(), sheet);
        setWidths(ThemesOutcomeColumns.values(), sheet);
        setWidths(DeltaColumns.values(), sheet);
    }

    private void setWidths(Column[] columns, Sheet sheet) {
        for (Column column : columns) {
            sheet.setColumnWidth(column.getColumnNumber(), column.getPreferredWidth());
        }
    }

    private int createContent(Sheet sheet, SingleThemeStatistic theme, WorkbookWrapper workbook) {
        int rowNum = FIRST_CONTENT_ROW;

        List<ThemeStatisticEntry> themeStatisticEntries = theme.getThemeEntries().stream()
                .filter(en -> en.getDate() != null).sorted(Comparator.comparing(ThemeStatisticEntry::getDate)).collect(Collectors.toList());
        List<ThemeStatisticEntry> themeStatisticEntriesWithoutDate = theme.getThemeEntries().stream()
                .filter(en -> en.getDate() == null).collect(Collectors.toList());

        themeStatisticEntries.addAll(themeStatisticEntriesWithoutDate);

        for (ThemeStatisticEntry entry : themeStatisticEntries) {
            Row row = sheet.createRow(rowNum);
            operationTypeMapper.getThemesRowWriter(entry).writeRow(workbook, row, entry);
            rowNum++;
        }
        return rowNum;
    }

    private void createHeaderRow(Sheet sheet, SingleThemeStatistic theme, WorkbookWrapper workbook) {
        Row headerRow = sheet.createRow(HEADER_ROW);

        sheet.addMergedRegion(new CellRangeAddress(HEADER_ROW,HEADER_ROW,1, 3));
        XlsUtils.writeStringValue(headerRow, ThemesIncomeColumns.THEME, "Приходы", workbook);

        sheet.addMergedRegion(new CellRangeAddress(HEADER_ROW, HEADER_ROW, 4, 7));
        XlsUtils.writeStringValue(headerRow, ThemesOutcomeColumns.THEME, "Расходы", workbook);

        XlsUtils.writeStringValue(headerRow, DeltaColumns.DELTA_TITLE, "Дельта", workbook);
        XlsUtils.writeDoubleValue(headerRow, DeltaColumns.DELTA_VALUE, theme.getDelta(), workbook);
    }

    private void createTitleRow(Sheet sheet, WorkbookWrapper workbook) {
        Row titleRow = sheet.createRow(TITLE_ROW);

        XlsUtils.writeStringValue(titleRow, ThemesIncomeColumns.DATE, "Дата", workbook);
        XlsUtils.writeStringValue(titleRow, ThemesIncomeColumns.THEME, "Тема", workbook);
        XlsUtils.writeStringValue(titleRow, ThemesIncomeColumns.SUM, "Сумма", workbook);
        XlsUtils.writeStringValue(titleRow, ThemesIncomeColumns.COMMENT, "Комментарий", workbook);
        XlsUtils.writeStringValue(titleRow, ThemesOutcomeColumns.THEME, "Тема", workbook);
        XlsUtils.writeStringValue(titleRow, ThemesOutcomeColumns.SUM, "Сумма", workbook);
        XlsUtils.writeStringValue(titleRow, ThemesOutcomeColumns.CONTRAGENT, "Контрагент", workbook);
        XlsUtils.writeStringValue(titleRow, ThemesOutcomeColumns.COMMENT, "Комментарий", workbook);
    }

    private void createFooterRow(Sheet sheet, SingleThemeStatistic theme, WorkbookWrapper workbook, int rowNum) {
        Row footerRow = sheet.createRow(rowNum);

        XlsUtils.writeStringValue(footerRow, ThemesIncomeColumns.THEME, "Итого:", workbook);
        XlsUtils.writeDoubleValue(footerRow, ThemesIncomeColumns.SUM, theme.getIncome(), workbook);

        XlsUtils.writeStringValue(footerRow, ThemesOutcomeColumns.THEME, "Итого:", workbook);
        XlsUtils.writeDoubleValue(footerRow, ThemesOutcomeColumns.SUM, theme.getOutcome(), workbook);
    }
}
