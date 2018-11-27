package com.blackmorse.controller.table;

import com.blackmorse.model.Statement;

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
        statementModel.setReceiver(statement.get("Получатель"));
        statementModel.setPaymentGoal(statement.get("НазначениеПлатежа"));

        String payer = statement.get("Плательщик");
        statementModel.setOperationType(Objects.equals(payer, "ООО \"Аналитприбор\"") ? "расход" : "приход");
        statementModel.setPayer(payer);
        return statementModel;
    }
}
