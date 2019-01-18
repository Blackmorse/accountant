package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.themes.columns.ThemesOutcomeColumns;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class OutcomeRowWriter extends AbstractRowWriter {
    @Override
    public void write(ThemeStatisticEntry entry, Sheet sheet, int rowNum, Workbook workbook,
                      CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        Row row = sheet.createRow(rowNum);
        DataFormat dataFormat = workbook.createDataFormat();

//        ThemesXlsUtils.createDateCell(row, ThemesOutcomeColumns.DATE.getColumnNumber(), entry.getDate(), workbook);
//        ThemesXlsUtils.createStringCell(row, ThemesOutcomeColumns.THEME.getColumnNumber(), entry.getTheme());
//        ThemesXlsUtils.createDoubleCell(row, ThemesOutcomeColumns.SUM.getColumnNumber(), entry.getSum());
//        ThemesXlsUtils.createStringCell(row, ThemesOutcomeColumns.CONTRAGENT.getColumnNumber(), entry.getReceiver());
//        ThemesXlsUtils.createStringCell(row, ThemesOutcomeColumns.COMMENT.getColumnNumber(), entry.getComment());

        XlsUtils.writeDateValue(workbook, row, ThemesOutcomeColumns.DATE, entry.getDate(), dataFormat, dateStyle);
        XlsUtils.writeStringValue(workbook, row, ThemesOutcomeColumns.THEME, entry.getTheme(), stringStyle);
        XlsUtils.writeDoubleValue(workbook, row, ThemesOutcomeColumns.SUM, entry.getSum(), dataFormat, doubleStyle);
        XlsUtils.writeStringValue(workbook, row, ThemesOutcomeColumns.CONTRAGENT, entry.getReceiver(), stringStyle);
        XlsUtils.writeStringValue(workbook, row, ThemesOutcomeColumns.COMMENT, entry.getComment(), stringStyle);
    }
}
