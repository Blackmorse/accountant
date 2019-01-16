package com.blackmorse.xls.writer.statement.outcome;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import com.blackmorse.xls.writer.statement.utils.XlsUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;

public class OutcomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme, String comment) {
        DataFormat format =  book.createDataFormat();

        XlsUtils.writeDateValue(book, row, OutcomeColumns.DATE, model.getDate(), format);
        XlsUtils.writeDoubleValue(book, row, OutcomeColumns.SUM, model.getSum(), format);
        XlsUtils.writeStringValue(book, row, OutcomeColumns.THEME, theme);
        XlsUtils.writeStringValue(book, row, OutcomeColumns.RECEIVER, model.getReceiver());
        XlsUtils.writeStringValue(book, row, OutcomeColumns.PAYER, model.getPayer());
        XlsUtils.writeStringValue(book, row, OutcomeColumns.COMMENTS, comment);
    }
}
