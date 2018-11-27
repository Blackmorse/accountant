package com.blackmorse.model;

import lombok.Data;

import java.util.Date;

@Data
public class StatementModel {
    private Integer number;
    private Date date;
    private Double sum;
    private String payer;
    private String payerBank;
    private String receiver;
    private String paymentGoal;
    private String operationType;
}
