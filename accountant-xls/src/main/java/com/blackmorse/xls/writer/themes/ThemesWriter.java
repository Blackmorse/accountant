package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.SingleThemeStatistic;
import com.blackmorse.model.themes.ThemesStatisticsHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ThemesWriter {
    private static final int HEADER_ROW = 0;
    private static final int TITLE_ROW = 1;

    public void writeFile(File file, ThemesStatisticsHolder themesHolder) {
        try(Workbook workbook = new HSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(file)) {

            for (SingleThemeStatistic theme : themesHolder.getStatistic()) {
                Sheet sheet = workbook.createSheet(theme.getTheme().replaceAll("\\?", "\\."));
                createHeaderRow(sheet);
                createTitleRow(sheet);
            }
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
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
