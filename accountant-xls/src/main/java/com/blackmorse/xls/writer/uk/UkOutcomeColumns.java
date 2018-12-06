package com.blackmorse.xls.writer.uk;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UkOutcomeColumns implements Column {
    SUM(7),
    COMMENT(9);

    @Getter
    private final int columnNumber;
}
