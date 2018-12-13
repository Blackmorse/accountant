package com.blackmorse.xls.writer.uk;

import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.xls.writer.WriterStrategy;
import com.blackmorse.xls.writer.utils.XlsUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;

public class UkOutcomeWriterStrategy implements WriterStrategy {
    @Override
    public void writeRow(HSSFWorkbook book, HSSFRow row, StatementModel model, String theme) {
        DataFormat format =  book.createDataFormat();

        XlsUtils.writeDoubleValue(book, row, UkOutcomeColumns.SUM, model.getSum(), format);
        XlsUtils.writeStringValue(book, row, UkOutcomeColumns.COMMENT, model.getPayer());
    }
}
