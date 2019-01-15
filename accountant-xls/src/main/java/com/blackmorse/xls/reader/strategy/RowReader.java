package com.blackmorse.xls.reader.strategy;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import org.apache.poi.ss.usermodel.Row;

public abstract class RowReader {
    public abstract ThemeStatisticEntry readStatisticEntry(Row row);
}