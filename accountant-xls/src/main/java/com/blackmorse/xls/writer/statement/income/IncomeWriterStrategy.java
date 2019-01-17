package com.blackmorse.xls.writer.statement.income;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import com.blackmorse.xls.writer.statement.utils.StatementXlsUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;

public class IncomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme, String comment) {
        DataFormat format =  book.createDataFormat();

        StatementXlsUtils.writeDateValue(book, row, StatementIncomeColumns.DATE, model.getDate(), format);
        StatementXlsUtils.writeDoubleValue(book, row, StatementIncomeColumns.SUM, model.getSum(), format);
        StatementXlsUtils.writeStringValue(book, row, StatementIncomeColumns.FIRM, model.getPayer());
        StatementXlsUtils.writeStringValue(book, row, StatementIncomeColumns.THEME, theme);
        StatementXlsUtils.writeStringValue(book, row, StatementIncomeColumns.COMMENTS, comment);
    }
}
