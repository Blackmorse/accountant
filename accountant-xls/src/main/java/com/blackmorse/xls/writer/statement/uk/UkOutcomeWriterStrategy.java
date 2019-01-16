package com.blackmorse.xls.writer.statement.uk;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.statement.WriterStrategy;
import com.blackmorse.xls.writer.statement.utils.XlsUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;

public class UkOutcomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme, String comment) {
        DataFormat format =  book.createDataFormat();

        XlsUtils.writeDoubleValue(book, row, UkOutcomeColumns.SUM, model.getSum(), format);
        XlsUtils.writeStringValue(book, row, UkOutcomeColumns.COMMENT, model.getPayer());
    }
}
