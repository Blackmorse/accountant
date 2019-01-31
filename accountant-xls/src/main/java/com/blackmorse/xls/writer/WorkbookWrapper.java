package com.blackmorse.xls.writer;

import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Getter
public class WorkbookWrapper implements AutoCloseable{
    private Workbook workbook;
    private CellStyle dateStyle;
    private CellStyle stringStyle;
    private CellStyle doubleStyle;
    private Map<Short, CellStyle> colorDoubleStyle = new HashMap<>();
    private BiConsumer<CellStyle, Workbook> cellStyler;
    private DataFormat format;

    public WorkbookWrapper(Workbook workbook, BiConsumer<CellStyle, Workbook> cellStyler) {
        this.workbook = workbook;
        this.cellStyler = cellStyler;
        format =  workbook.createDataFormat();

        this.dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.MM.yyyy"));
        cellStyler.accept(dateStyle, workbook);

        this.stringStyle = workbook.createCellStyle();
        cellStyler.accept(stringStyle, workbook);

        this.doubleStyle = workbook.createCellStyle();
        doubleStyle.setDataFormat(format.getFormat("#,##0.00"));
        cellStyler.accept(doubleStyle, workbook);
    }

    public CellStyle getColorDoubleStyle(short color) {
        if (color == 64) color = 9; //Костыль. Иначе иногда белое становится черным
        return colorDoubleStyle.computeIfAbsent(color, col -> {
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(format.getFormat("#,##0.00"));
            cellStyler.accept(cellStyle, workbook);
            cellStyle.setFillForegroundColor(col);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            return cellStyle;
        });
    }

    @Override
    public void close() throws IOException {
        workbook.close();
    }
}
