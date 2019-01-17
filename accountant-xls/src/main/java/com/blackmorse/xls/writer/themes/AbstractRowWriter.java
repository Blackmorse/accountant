package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractRowWriter {
    public abstract void write(ThemeStatisticEntry entry, Sheet sheet, int rowNum, Workbook workbook);
}
