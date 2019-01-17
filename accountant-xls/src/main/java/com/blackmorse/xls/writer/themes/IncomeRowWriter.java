package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.themes.columns.ThemesIncomeColumns;
import com.blackmorse.xls.writer.themes.utils.ThemesXlsUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class IncomeRowWriter extends AbstractRowWriter{
    @Override
    public void write(ThemeStatisticEntry entry, Sheet sheet, int rowNum, Workbook workbook) {
        Row row = sheet.createRow(rowNum);

        ThemesXlsUtils.createDateCell(row, ThemesIncomeColumns.DATE.getColumnNumber(), entry.getDate(), workbook);
        ThemesXlsUtils.createStringCell(row, ThemesIncomeColumns.THEME.getColumnNumber(), entry.getTheme());
        ThemesXlsUtils.createDoubleCell(row, ThemesIncomeColumns.SUM.getColumnNumber(), entry.getSum());
        ThemesXlsUtils.createStringCell(row, ThemesIncomeColumns.COMMENT.getColumnNumber(), entry.getComment());
    }
}
