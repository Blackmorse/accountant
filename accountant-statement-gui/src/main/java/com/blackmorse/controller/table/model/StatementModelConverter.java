package com.blackmorse.controller.table.model;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.model.Statement;
import com.blackmorse.model.StatementModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Singleton
@Slf4j
public class StatementModelConverter {

    private Configuration configuration;

    @Inject
    public StatementModelConverter(Configuration configuration) {
        this.configuration = configuration;
    }

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public StatementModel convert(Statement statement) {
        StatementModel statementModel = new StatementModel();
        statementModel.setNumber(Integer.valueOf(statement.get("Номер")));
        try {
            statementModel.setDate(FORMAT.parse(statement.get("Дата")));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        statementModel.setSum(Double.valueOf(statement.get("Сумма")));

        statementModel.setPayerBank(statement.get("ПлательщикБанк1"));
        statementModel.setReceiver(ObjectUtils.firstNonNull(statement.get("Получатель"), statement.get("Получатель1")));
        statementModel.setPaymentGoal(statement.get("НазначениеПлатежа"));

        String payer = ObjectUtils.firstNonNull(statement.get("Плательщик"), statement.get("Плательщик1"));
        statementModel.setOperationType(isOutcome(payer)? "расход" : "приход");
        statementModel.setPayer(payer);
        return statementModel;
    }

    private boolean isOutcome(String payer) {
        for (String firm : configuration.getFirms()) {
            if (payer.toUpperCase().contains(firm.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}