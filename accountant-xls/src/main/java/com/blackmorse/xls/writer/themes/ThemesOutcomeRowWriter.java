package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.blackmorse.xls.writer.themes.columns.ThemesOutcomeColumns;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.Row;

public class ThemesOutcomeRowWriter implements ThemesRowWriter {
    @Override
    public void writeRow(WorkbookWrapper workbook, Row row, ThemeStatisticEntry model) {
        XlsUtils.writeDateValue(row, ThemesOutcomeColumns.DATE, model.getDate(), workbook);
        XlsUtils.writeStringValue(row, ThemesOutcomeColumns.THEME, model.getTheme(), workbook);
        XlsUtils.writeDoubleValueWithColor(row, ThemesOutcomeColumns.SUM, model.getSum(), model.getSumColor(), workbook);
        XlsUtils.writeStringValue(row, ThemesOutcomeColumns.CONTRAGENT, model.getReceiver(), workbook);
        XlsUtils.writeStringValue(row, ThemesOutcomeColumns.COMMENT, model.getComment(), workbook);
    }
}
