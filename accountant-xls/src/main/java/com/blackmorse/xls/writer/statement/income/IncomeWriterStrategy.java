package com.blackmorse.xls.writer.statement.income;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import com.blackmorse.xls.writer.statement.utils.XlsUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;

public class IncomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme) {
        DataFormat format =  book.createDataFormat();

        XlsUtils.writeDateValue(book, row, IncomeColumns.DATE, model.getDate(), format);
        XlsUtils.writeDoubleValue(book, row, IncomeColumns.SUM, model.getSum(), format);
        XlsUtils.writeStringValue(book, row, IncomeColumns.FIRM, model.getPayer());
        XlsUtils.writeStringValue(book, row, IncomeColumns.THEME, theme);
    }
}
