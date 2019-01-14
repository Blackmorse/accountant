package com.blackmorse.xls.writer.statement;

import com.blackmorse.model.OperationType;
import com.blackmorse.xls.writer.statement.income.IncomeWriterStrategy;
import com.blackmorse.xls.writer.statement.outcome.OutcomeWriterStrategy;
import com.blackmorse.xls.writer.statement.uk.UkIncomeWriterStrategy;
import com.blackmorse.xls.writer.statement.uk.UkOutcomeWriterStrategy;

import javax.inject.Singleton;

@Singleton
public class WriterStrategyFactory {
    private static final String UK = "УК";

    public WriterStrategy createStrategy(OperationType operationType, String sheetName) {
        if (OperationType.INCOME.equals(operationType)) {
            if (UK.equals(sheetName)) {
                return new UkIncomeWriterStrategy();
            } else {
                return new IncomeWriterStrategy();
            }
        }
         else {
            if (UK.equals(sheetName)) {
                return new UkOutcomeWriterStrategy();
            } else {
                return new OutcomeWriterStrategy();
            }
        }

    }
}
