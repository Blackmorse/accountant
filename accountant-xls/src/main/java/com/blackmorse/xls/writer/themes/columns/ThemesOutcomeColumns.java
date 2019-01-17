package com.blackmorse.xls.writer.themes.columns;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ThemesOutcomeColumns implements Column {
    DATE(0),
    THEME(4),
    SUM(5),
    CONTRAGENT(6),
    COMMENT(7);

    @Getter
    private final int columnNumber;
}
