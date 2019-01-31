package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.blackmorse.xls.writer.themes.columns.ThemesIncomeColumns;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class ThemesIncomeRowWriter implements ThemesRowWriter {
    @Override
    public void writeRow(WorkbookWrapper workbook, Row row, ThemeStatisticEntry model) {
        XlsUtils.writeDateValue(row, ThemesIncomeColumns.DATE, model.getDate(), workbook);
        XlsUtils.writeStringValue(row, ThemesIncomeColumns.THEME, model.getTheme(), workbook);
        XlsUtils.writeDoubleValueWithColor(row, ThemesIncomeColumns.SUM, model.getSum(), model.getSumColor(), workbook);
        XlsUtils.writeStringValue(row, ThemesIncomeColumns.COMMENT, model.getComment(), workbook);
    }
}
