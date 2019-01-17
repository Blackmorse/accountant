package com.blackmorse.xls.writer.statement.outcome;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatementOutcomeColumns implements Column {
    DATE(0),
    SUM(6),
    THEME(7),
    RECEIVER(8),
    PAYER(9),
    COMMENTS(10);

    @Getter
    private final int columnNumber;
}
