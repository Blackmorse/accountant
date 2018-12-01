package com.blackmorse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class StatementModel {
    @AllArgsConstructor
    public enum OperationType {
        INCOME("приход"),
        OUTCOME("расход");

        @Getter
        private final String stringValue;
    }

    private Integer number;
    private Date date;
    private Double sum;
    private String payer;
    private String payerBank;
    private String receiver;
    private String paymentGoal;
    private OperationType operationType;
}
