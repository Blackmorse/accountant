package com.blackmorse.xls.writer.themes.columns;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ThemesIncomeColumns implements Column {
    DATE(0, 3000),
    THEME(1, 3000),
    SUM(2, 4500),
    COMMENT(3, 5000);

    @Getter
    private final int columnNumber;
    @Getter
    private final int preferredWidth;
}
