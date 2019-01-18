package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.themes.columns.ThemesOutcomeColumns;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class ThemesOutcomeRowWriter implements ThemesRowWriter {
    @Override
    public void writeRow(Workbook workbook, Row row, ThemeStatisticEntry model,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        DataFormat dataFormat = workbook.createDataFormat();

        XlsUtils.writeDateValue(row, ThemesOutcomeColumns.DATE, model.getDate(), dataFormat, dateStyle);
        XlsUtils.writeStringValue(row, ThemesOutcomeColumns.THEME, model.getTheme(), stringStyle);
        XlsUtils.writeDoubleValue(row, ThemesOutcomeColumns.SUM, model.getSum(), dataFormat, doubleStyle);
        XlsUtils.writeStringValue(row, ThemesOutcomeColumns.CONTRAGENT, model.getReceiver(), stringStyle);
        XlsUtils.writeStringValue(row, ThemesOutcomeColumns.COMMENT, model.getComment(), stringStyle);
    }
}
