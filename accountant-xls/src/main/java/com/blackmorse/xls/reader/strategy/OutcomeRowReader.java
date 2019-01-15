package com.blackmorse.xls.reader.strategy;

import com.blackmorse.model.OperationType;
import com.blackmorse.model.themes.ThemeStatisticEntry;
import com.blackmorse.xls.writer.statement.outcome.OutcomeColumns;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.Optional;

public class OutcomeRowReader extends RowReader {
    @Override
    public ThemeStatisticEntry readStatisticEntry(Row row) {
        Cell themeCell = row.getCell(OutcomeColumns.THEME.getColumnNumber());
        Cell sumCell = row.getCell(OutcomeColumns.SUM.getColumnNumber());
        Cell dateCell = row.getCell(OutcomeColumns.DATE.getColumnNumber());
        Cell commentCell = row.getCell(OutcomeColumns.COMMENTS.getColumnNumber());
        Cell receiverCell = row.getCell(OutcomeColumns.RECEIVER.getColumnNumber());
        if (themeCell != null && themeCell.getCellType() == CellType.STRING) {
            String theme = themeCell.getStringCellValue();
            Double sum = Optional.ofNullable(sumCell).map(Cell::getNumericCellValue).orElse(null);
            Date date = Optional.ofNullable(dateCell).map(Cell::getDateCellValue).orElse(null);
            String comment = Optional.ofNullable(commentCell).map(Cell::getStringCellValue).orElse(null);
            String receiver = Optional.ofNullable(receiverCell).map(Cell::getStringCellValue).orElse(null);
            if (theme != null && !theme.isEmpty()) {
                return new ThemeStatisticEntry(theme, sum, OperationType.OUTCOME,
                        date, comment, receiver);
            }
        }
        return null;
    }
}
