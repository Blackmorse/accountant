package com.blackmorse.xls.writer.outcome;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OutcomeColumns {
    DATE(0),
    SUM(6),
    THEME(7),
    RECEIVER(8),
    PAYER(9),
    COMMENT(10);

    private final int columnNumber;

    public int getColumnNumber() {
        return columnNumber;
    }
}
