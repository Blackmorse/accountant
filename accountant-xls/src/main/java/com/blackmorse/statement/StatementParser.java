package com.blackmorse.statement;

import com.blackmorse.model.Statement;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class StatementParser {
    private static final String NEW_LINE = "\r\n";
    private static final String SECTION_DOCUMENT = "СекцияДокумент";
    private static final String END_DOCUMENT = "КонецДокумента" + NEW_LINE;
    private static final String FILE_END = "КонецФайла";


    public List<Statement> parse(String text) {
        List<Statement> statements = new ArrayList<>();

        text = deleteHeaderAndFooter(text);
        List<String> statementsText = splitBody(text);
        for (String statementText : statementsText) {
            Statement statement = new Statement();
            String[] entries = statementText.split(NEW_LINE);
            for (String entry : entries) {
                String[] keyValue = entry.split("=");
                if (keyValue.length == 1) {
                    statement.put(keyValue[0], null);
                } else
                    statement.put(keyValue[0], keyValue[1]);
            }
            statements.add(statement);
        }
        return statements;
    }

    private String deleteHeaderAndFooter(String text) {
        return text.substring(0, text.lastIndexOf(FILE_END)).substring(text.indexOf(SECTION_DOCUMENT))
                .replaceAll(NEW_LINE + NEW_LINE, NEW_LINE);
    }

    private List<String> splitBody(String text) {
        return Arrays.asList(text.split(END_DOCUMENT));
    }
}
