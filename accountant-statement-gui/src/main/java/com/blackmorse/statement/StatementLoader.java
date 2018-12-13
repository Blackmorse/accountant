package com.blackmorse.statement;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.model.statement.Statement;
import com.blackmorse.utils.FileUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class StatementLoader {
    private Configuration configuration;
    private StatementParser statementParser;

    @Inject
    public StatementLoader(Configuration configuration, StatementParser statementParser) {
        this.configuration = configuration;
        this.statementParser = statementParser;
    }

    public List<Statement> load() throws IOException, URISyntaxException {
        List<Statement> result = new ArrayList<>();
        for (String path : configuration.getStatementPaths()) {
            String text = new String(Files.readAllBytes(FileUtils.getFileFromString(path).toPath()), "windows-1251");
            List<Statement> statements = statementParser.parse(text);
            result.addAll(statements);
        }
        return result;
    }
}
