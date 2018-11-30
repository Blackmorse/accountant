package com.blackmorse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class StatementModel {
    @AllArgsConstructor
    public enum OperationType {
        INCOME("приход"),
        OUTCOME("расход");

        private final String stringValue;

        public String getStringValue() {
            return stringValue;
        }
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
