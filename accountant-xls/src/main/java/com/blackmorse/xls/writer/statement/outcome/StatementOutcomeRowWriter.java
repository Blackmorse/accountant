package com.blackmorse.xls.writer.statement.outcome;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.blackmorse.xls.writer.statement.StatementRowWriter;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class StatementOutcomeRowWriter implements StatementRowWriter {
    @Override
    public void writeRow(WorkbookWrapper book, Row row, OutputStatementEntry model) {
        XlsUtils.writeDateValue(row, StatementOutcomeColumns.DATE, model.getStatementModel().getDate(), book);
        XlsUtils.writeDoubleValue(row, StatementOutcomeColumns.SUM, model.getStatementModel().getSum(), book);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.THEME, model.getTheme(), book);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.RECEIVER, model.getStatementModel().getReceiver(), book);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.PAYER, model.getStatementModel().getPayer(), book);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.COMMENTS, model.getComment(), book);
    }
}
