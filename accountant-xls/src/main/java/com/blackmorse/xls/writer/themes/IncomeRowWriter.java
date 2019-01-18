package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.themes.columns.ThemesIncomeColumns;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class IncomeRowWriter extends AbstractRowWriter{
    @Override
    public void write(ThemeStatisticEntry entry, Sheet sheet, int rowNum, Workbook workbook,
                      CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        Row row = sheet.createRow(rowNum);

        XlsUtils.writeDateValue(workbook, row, ThemesIncomeColumns.DATE, entry.getDate(), format, dateStyle);
        XlsUtils.writeStringValue(workbook, row, ThemesIncomeColumns.THEME, entry.getTheme(), stringStyle);
        XlsUtils.writeDoubleValue(workbook, row, ThemesIncomeColumns.SUM, entry.getSum(), format, doubleStyle);
        XlsUtils.writeStringValue(workbook, row, ThemesIncomeColumns.COMMENT, entry.getComment(), stringStyle);
    }
}
