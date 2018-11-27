package com.blackmorse.controller.table.model;

import lombok.Data;

@Data
public class StatementModel {
    private String number;
    private String date;
    private String sum;
    private String payer;
    private String payerBank;
    private String receiver;
    private String paymentGoal;
    private String operationType;
}
