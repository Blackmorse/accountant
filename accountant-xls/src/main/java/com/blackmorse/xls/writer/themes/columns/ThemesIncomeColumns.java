package com.blackmorse.xls.writer.themes.columns;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ThemesIncomeColumns implements Column {
    DATE(0),
    THEME(1),
    SUM(2),
    COMMENT(3);

    @Getter
    private final int columnNumber;
}
