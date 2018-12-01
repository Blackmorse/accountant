package com.blackmorse.xls.writer.outcome;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OutcomeColumns {
    DATE(0),
    SUM(6),
    THEME(7),
    RECEIVER(8),
    PAYER(9),
    COMMENT(10);

    @Getter
    private final int columnNumber;
}
