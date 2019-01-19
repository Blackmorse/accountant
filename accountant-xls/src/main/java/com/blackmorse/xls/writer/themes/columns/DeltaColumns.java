package com.blackmorse.xls.writer.themes.columns;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeltaColumns implements Column {
    DELTA_TITLE(8),
    DELTA_VALUE(9);

    @Getter
    private final int columnNumber;
}
