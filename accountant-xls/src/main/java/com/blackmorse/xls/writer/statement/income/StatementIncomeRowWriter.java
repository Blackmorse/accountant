package com.blackmorse.xls.writer.statement.income;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.writer.statement.StatementRowWriter;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class StatementIncomeRowWriter implements StatementRowWriter {
    @Override
    public void writeRow(Workbook book, Row row, OutputStatementEntry model,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        XlsUtils.writeDateValue(row, StatementIncomeColumns.DATE, model.getStatementModel().getDate(), format, dateStyle);
        XlsUtils.writeDoubleValue(row, StatementIncomeColumns.SUM, model.getStatementModel().getSum(), format, doubleStyle);
        XlsUtils.writeStringValue(row, StatementIncomeColumns.FIRM, model.getStatementModel().getPayer(), stringStyle);
        XlsUtils.writeStringValue(row, StatementIncomeColumns.THEME, model.getTheme(), stringStyle);
        XlsUtils.writeStringValue(row, StatementIncomeColumns.COMMENTS, model.getComment(), stringStyle);
    }
}
