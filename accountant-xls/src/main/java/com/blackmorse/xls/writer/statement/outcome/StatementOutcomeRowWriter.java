package com.blackmorse.xls.writer.statement.outcome;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.writer.statement.StatementRowWriter;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class StatementOutcomeRowWriter implements StatementRowWriter {
    @Override
    public void writeRow(Workbook book, Row row, OutputStatementEntry model,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        XlsUtils.writeDateValue(row, StatementOutcomeColumns.DATE, model.getStatementModel().getDate(), format, dateStyle);
        XlsUtils.writeDoubleValue(row, StatementOutcomeColumns.SUM, model.getStatementModel().getSum(), format, doubleStyle);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.THEME, model.getTheme(), stringStyle);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.RECEIVER, model.getStatementModel().getReceiver(), stringStyle);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.PAYER, model.getStatementModel().getPayer(), stringStyle);
        XlsUtils.writeStringValue(row, StatementOutcomeColumns.COMMENTS, model.getComment(), stringStyle);
    }
}
