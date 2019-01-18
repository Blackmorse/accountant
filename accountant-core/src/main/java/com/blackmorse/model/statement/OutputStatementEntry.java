package com.blackmorse.model.statement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutputStatementEntry {
    private StatementModel statementModel;
    private String theme;
    private String comment;
}
