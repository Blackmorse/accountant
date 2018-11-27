package com.blackmorse.controller.table.model;

import com.blackmorse.model.Statement;
import org.apache.commons.lang3.ObjectUtils;

import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class StatementModelConverter {

    public StatementModel convert(Statement statement) {
        StatementModel statementModel = new StatementModel();
        statementModel.setNumber(statement.get("Номер"));
        statementModel.setDate(statement.get("Дата"));
        statementModel.setSum(statement.get("Сумма"));

        statementModel.setPayerBank(statement.get("ПлательщикБанк1"));
        statementModel.setReceiver(ObjectUtils.firstNonNull(statement.get("Получатель"), statement.get("Получатель1")));
        statementModel.setPaymentGoal(statement.get("НазначениеПлатежа"));

        String payer = ObjectUtils.firstNonNull(statement.get("Плательщик"), statement.get("Плательщик1"));
        statementModel.setOperationType(Objects.equals(payer, "ООО \"Аналитприбор\"") ? "расход" : "приход");
        statementModel.setPayer(payer);
        return statementModel;
    }
}
