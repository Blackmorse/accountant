package com.blackmorse.xls.writer.statement.income;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatementIncomeColumns implements Column {
    DATE(0),
    SUM(1),
    CREDITS(2),
    FIRM(4),
    THEME(5),
    COMMENTS(10);

    @Getter
    private final int columnNumber;
    @Getter
    private int preferredWidth = 0;
}
