package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.themes.columns.ThemesIncomeColumns;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class ThemesIncomeRowWriter implements ThemesRowWriter {
    @Override
    public void writeRow(Workbook workbook, Row row, ThemeStatisticEntry model,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        XlsUtils.writeDateValue(row, ThemesIncomeColumns.DATE, model.getDate(), format, dateStyle);
        XlsUtils.writeStringValue(row, ThemesIncomeColumns.THEME, model.getTheme(), stringStyle);
        XlsUtils.writeDoubleValue(row, ThemesIncomeColumns.SUM, model.getSum(), format, doubleStyle);
        XlsUtils.writeStringValue(row, ThemesIncomeColumns.COMMENT, model.getComment(), stringStyle);
    }
}
