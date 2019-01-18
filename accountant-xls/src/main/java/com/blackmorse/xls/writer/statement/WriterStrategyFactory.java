package com.blackmorse.xls.writer.statement;

import com.blackmorse.model.OperationType;
import com.blackmorse.xls.writer.statement.income.StatementIncomeRowWriter;
import com.blackmorse.xls.writer.statement.outcome.StatementOutcomeRowWriter;
import com.blackmorse.xls.writer.statement.uk.StatementUkIncomeRowWriter;
import com.blackmorse.xls.writer.statement.uk.StatementUkOutcomeWriter;

import javax.inject.Singleton;

@Singleton
public class WriterStrategyFactory {
    private static final String UK = "УК";

    public StatementRowWriter createStrategy(OperationType operationType, String sheetName) {
        if (OperationType.INCOME.equals(operationType)) {
            if (UK.equals(sheetName)) {
                return new StatementUkIncomeRowWriter();
            } else {
                return new StatementIncomeRowWriter();
            }
        }
         else {
            if (UK.equals(sheetName)) {
                return new StatementUkOutcomeWriter();
            } else {
                return new StatementOutcomeRowWriter();
            }
        }

    }
}
