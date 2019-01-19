package com.blackmorse.xls.writer.statement.uk;

import com.blackmorse.model.statement.OutputStatementEntry;
import com.blackmorse.xls.writer.WorkbookWrapper;
import com.blackmorse.xls.writer.statement.StatementRowWriter;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.ss.usermodel.Row;

public class StatementUkOutcomeWriter implements StatementRowWriter {
    @Override
    public void writeRow(WorkbookWrapper book, Row row, OutputStatementEntry model) {
        XlsUtils.writeDoubleValue(row, StatementUkOutcomeColumns.SUM, model.getStatementModel().getSum(), book);
        XlsUtils.writeStringValue(row, StatementUkOutcomeColumns.COMMENT, model.getStatementModel().getPayer(), book);
    }
}
