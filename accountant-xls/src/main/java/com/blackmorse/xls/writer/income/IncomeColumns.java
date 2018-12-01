package com.blackmorse.xls.writer.income;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum IncomeColumns {
    DATE(0),
    SUM(1),
    CREDITS(2),
    COMMENTS(3),
    FIRM(4),
    THEME(5);

    @Getter
    private final int columnNumber;
}
