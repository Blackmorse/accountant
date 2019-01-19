package com.blackmorse.xls.writer;

import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.function.BiConsumer;

@Getter
public class WorkbookWrapper implements AutoCloseable{
    private Workbook workbook;
    private CellStyle dateStyle;
    private CellStyle stringStyle;
    private CellStyle doubleStyle;

    public WorkbookWrapper(Workbook workbook, BiConsumer<CellStyle, Workbook> cellStyler) {
        this.workbook = workbook;
        DataFormat format =  workbook.createDataFormat();

        this.dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.MM.yyyy"));
        cellStyler.accept(dateStyle, workbook);

        this.stringStyle = workbook.createCellStyle();
        cellStyler.accept(stringStyle, workbook);

        this.doubleStyle = workbook.createCellStyle();
        doubleStyle.setDataFormat(format.getFormat("#,##0.00"));
        cellStyler.accept(doubleStyle, workbook);
    }

    @Override
    public void close() throws IOException {
        workbook.close();
    }
}
