package com.blackmorse.xls.reader.strategy;

import com.blackmorse.model.themes.ThemeStatisticEntry;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;

public abstract class RowReader {
    //State variable. Last written Date
    protected Date lastDate;

    public abstract ThemeStatisticEntry readStatisticEntry(Row row);
}