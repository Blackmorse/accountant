package com.blackmorse.xls.writer.themes.columns;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeltaColumns implements Column {
    DELTA_TITLE(8, 3000),
    DELTA_VALUE(9, 4500);

    @Getter
    private final int columnNumber;
    @Getter
    private final int preferredWidth;
}
