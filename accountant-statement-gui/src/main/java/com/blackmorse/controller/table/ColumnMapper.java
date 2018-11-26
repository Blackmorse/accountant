package com.blackmorse.controller.table;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ColumnMapper {
    private static final Map<String, String> columnsToKeys = new HashMap<>();

    public ColumnMapper() {
        columnsToKeys.put("Номер", "Номер");
        columnsToKeys.put("Дата", "Дата");
        columnsToKeys.put("Сумма", "Сумма");
        columnsToKeys.put("Плательщик", "Плательщик");
        columnsToKeys.put("Банк Плательщика", "ПлательщикБанк1");
        columnsToKeys.put("Получатель", "Получатель");
        columnsToKeys.put("Назначение платежа", "НазначениеПлатежа");
        columnsToKeys.put("Тип операции", "Плательщик");
    }

    private String getValue(String key) {
        return columnsToKeys.get(key);
    }
}
