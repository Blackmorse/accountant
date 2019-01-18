package com.blackmorse.xls.writer.statement.income;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class IncomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(Workbook book, Row row, StatementModel model, String theme, String comment,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        XlsUtils.writeDateValue(book, row, StatementIncomeColumns.DATE, model.getDate(), format, dateStyle);
        XlsUtils.writeDoubleValue(book, row, StatementIncomeColumns.SUM, model.getSum(), format, doubleStyle);
        XlsUtils.writeStringValue(book, row, StatementIncomeColumns.FIRM, model.getPayer(), stringStyle);
        XlsUtils.writeStringValue(book, row, StatementIncomeColumns.THEME, theme, stringStyle);
        XlsUtils.writeStringValue(book, row, StatementIncomeColumns.COMMENTS, comment, stringStyle);
    }
}
