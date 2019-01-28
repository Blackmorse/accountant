package com.blackmorse.xls.reader.strategy;

import com.blackmorse.model.OperationType;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.statement.income.StatementIncomeColumns;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.Optional;

public class IncomeRowReader extends RowReader {
    @Override
    public ThemeStatisticEntry readStatisticEntry(Row row) {
        Cell themeCell = row.getCell(StatementIncomeColumns.THEME.getColumnNumber());
        Cell sumCell = row.getCell(StatementIncomeColumns.SUM.getColumnNumber());
        Cell dateCell = row.getCell(StatementIncomeColumns.DATE.getColumnNumber());
        if (themeCell != null && themeCell.getCellType() == CellType.STRING) {
            String theme = themeCell.getStringCellValue();
            Double sum = Optional.ofNullable(sumCell).map(Cell::getNumericCellValue).orElse(null);
            Date date = Optional.ofNullable(dateCell).map(Cell::getDateCellValue).orElse(lastDate);
            lastDate = date;
            if (theme != null && !theme.isEmpty()) {
                return new ThemeStatisticEntry(theme, sum, OperationType.INCOME,
                        date, null, null);
            }
        }
        return null;
    }
}
