package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.themes.columns.ThemesOutcomeColumns;
import com.blackmorse.xls.writer.themes.utils.ThemesXlsUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class OutcomeRowWriter extends AbstractRowWriter {
    @Override
    public void write(ThemeStatisticEntry entry, Sheet sheet, int rowNum, Workbook workbook) {
        Row row = sheet.createRow(rowNum);

        ThemesXlsUtils.createDateCell(row, ThemesOutcomeColumns.DATE.getColumnNumber(), entry.getDate(), workbook);
        ThemesXlsUtils.createStringCell(row, ThemesOutcomeColumns.THEME.getColumnNumber(), entry.getTheme());
        ThemesXlsUtils.createDoubleCell(row, ThemesOutcomeColumns.SUM.getColumnNumber(), entry.getSum());
        ThemesXlsUtils.createStringCell(row, ThemesOutcomeColumns.CONTRAGENT.getColumnNumber(), entry.getReceiver());
        ThemesXlsUtils.createStringCell(row, ThemesOutcomeColumns.COMMENT.getColumnNumber(), entry.getComment());
    }
}
