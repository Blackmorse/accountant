package com.blackmorse.xls.writer.statement.income;

import com.blackmorse.xls.writer.statement.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum IncomeColumns implements Column {
    DATE(0),
    SUM(1),
    CREDITS(2),
    FIRM(4),
    THEME(5);

    @Getter
    private final int columnNumber;
}
