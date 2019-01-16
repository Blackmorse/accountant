package com.blackmorse.xls;

import com.blackmorse.model.OperationType;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.themes.AbstractRowWriter;
import com.blackmorse.xls.writer.themes.IncomeRowWriter;
import com.blackmorse.xls.writer.themes.OutcomeRowWriter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class OperationTypeMapper {
    private final Map<OperationType, AbstractRowWriter> writerMap = new HashMap<>();

    @Inject
    public OperationTypeMapper(IncomeRowWriter incomeRowWriter, OutcomeRowWriter outcomeRowWriter) {
        writerMap.put(OperationType.OUTCOME, outcomeRowWriter);
        writerMap.put(OperationType.INCOME, incomeRowWriter);
    }

    public AbstractRowWriter getRowWriter(ThemeStatisticEntry themeEntry) {
        return writerMap.get(themeEntry.getOperationType());
    }
}
