package com.blackmorse.xls.writer.statement.outcome;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.*;

public class OutcomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(Workbook book, Row row, StatementModel model, String theme, String comment,
                         CellStyle dateStyle, CellStyle stringStyle, CellStyle doubleStyle, DataFormat format) {
        XlsUtils.writeDateValue(book, row, StatementOutcomeColumns.DATE, model.getDate(), format, dateStyle);
        XlsUtils.writeDoubleValue(book, row, StatementOutcomeColumns.SUM, model.getSum(), format, doubleStyle);
        XlsUtils.writeStringValue(book, row, StatementOutcomeColumns.THEME, theme, stringStyle);
        XlsUtils.writeStringValue(book, row, StatementOutcomeColumns.RECEIVER, model.getReceiver(), stringStyle);
        XlsUtils.writeStringValue(book, row, StatementOutcomeColumns.PAYER, model.getPayer(), stringStyle);
        XlsUtils.writeStringValue(book, row, StatementOutcomeColumns.COMMENTS, comment, stringStyle);
    }
}
