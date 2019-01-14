package com.blackmorse.xls.writer.statement.outcome;

import com.blackmorse.xls.writer.statement.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OutcomeColumns implements Column {
    DATE(0),
    SUM(6),
    THEME(7),
    RECEIVER(8),
    PAYER(9),
    COMMENT(10);

    @Getter
    private final int columnNumber;
}
