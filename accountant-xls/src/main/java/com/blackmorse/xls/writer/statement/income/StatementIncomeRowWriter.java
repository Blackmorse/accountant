package com.blackmorse.xls.writer.statement.income;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.blackmorse.xls.writer.statement.StatementRowWriter;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.Row;

public class StatementIncomeRowWriter implements StatementRowWriter {
    @Override
    public void writeRow(WorkbookWrapper book, Row row, OutputStatementEntry model) {
        XlsUtils.writeDateValue(row, StatementIncomeColumns.DATE, model.getStatementModel().getDate(), book);
        XlsUtils.writeDoubleValue(row, StatementIncomeColumns.SUM, model.getStatementModel().getSum(), book);
        XlsUtils.writeStringValue(row, StatementIncomeColumns.FIRM, model.getStatementModel().getPayer(), book);
        XlsUtils.writeStringValue(row, StatementIncomeColumns.THEME, model.getTheme(), book);
        XlsUtils.writeStringValue(row, StatementIncomeColumns.COMMENTS, model.getComment(), book);
    }
}
