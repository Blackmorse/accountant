package com.blackmorse.xls.writer.statement.outcome;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import com.blackmorse.xls.writer.statement.utils.StatementXlsUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;

public class OutcomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme, String comment) {
        DataFormat format =  book.createDataFormat();

        StatementXlsUtils.writeDateValue(book, row, StatementOutcomeColumns.DATE, model.getDate(), format);
        StatementXlsUtils.writeDoubleValue(book, row, StatementOutcomeColumns.SUM, model.getSum(), format);
        StatementXlsUtils.writeStringValue(book, row, StatementOutcomeColumns.THEME, theme);
        StatementXlsUtils.writeStringValue(book, row, StatementOutcomeColumns.RECEIVER, model.getReceiver());
        StatementXlsUtils.writeStringValue(book, row, StatementOutcomeColumns.PAYER, model.getPayer());
        StatementXlsUtils.writeStringValue(book, row, StatementOutcomeColumns.COMMENTS, comment);
    }
}
