package com.blackmorse.xls.writer;

import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.writer.income.IncomeWriterStrategy;
import com.blackmorse.xls.writer.outcome.OutcomeWriterStrategy;
import com.blackmorse.xls.writer.uk.UkIncomeWriterStrategy;
import com.blackmorse.xls.writer.uk.UkOutcomeWriterStrategy;

import javax.inject.Singleton;

@Singleton
public class WriterStrategyFactory {
    public static final String UK = "УК";

    public WriterStrategy createStrategy(StatementModel.OperationType operationType, String sheetName) {
        if (StatementModel.OperationType.INCOME.equals(operationType)) {
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
