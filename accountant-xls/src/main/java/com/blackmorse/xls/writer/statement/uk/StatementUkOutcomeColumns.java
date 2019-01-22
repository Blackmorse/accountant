package com.blackmorse.xls.writer.statement.uk;

import com.blackmorse.xls.writer.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatementUkOutcomeColumns implements Column {
    SUM(7),
    COMMENT(9);

    @Getter
    private final int columnNumber;
    @Getter
    private int preferredWidth = 0;
}
