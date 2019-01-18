package com.blackmorse.xls.writer.themes;

import com.blackmorse.model.OperationType;
import com.blackmorse.model.themes.ThemeStatisticEntry;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class OperationTypeMapper {
    private final Map<OperationType, ThemesRowWriter> writerMap = new HashMap<>();

    @Inject
    public OperationTypeMapper(ThemesIncomeRowWriter incomeRowWriter, ThemesOutcomeRowWriter outcomeRowWriter) {
        writerMap.put(OperationType.OUTCOME, outcomeRowWriter);
        writerMap.put(OperationType.INCOME, incomeRowWriter);
    }

    public ThemesRowWriter getThemesRowWriter(ThemeStatisticEntry themeEntry) {
        return writerMap.get(themeEntry.getOperationType());
    }
}
