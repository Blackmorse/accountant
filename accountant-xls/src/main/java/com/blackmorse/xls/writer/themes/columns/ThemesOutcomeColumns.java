package com.blackmorse.xls.writer.themes.columns;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ThemesOutcomeColumns implements Column {
    DATE(0, 3000),
    THEME(4, 3000),
    SUM(5, 4500),
    CONTRAGENT(6, 10000),
    COMMENT(7, 5000);

    @Getter
    private final int columnNumber;
    @Getter
    private final int preferredWidth;
}
