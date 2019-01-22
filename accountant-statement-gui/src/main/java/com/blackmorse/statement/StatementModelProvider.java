package com.blackmorse.statement;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.controller.table.model.StatementModelConverter;
import com.blackmorse.model.statement.Statements;
import com.blackmorse.model.statement.StatementModel;
import com.blackmorse.utils.FileUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class StatementModelProvider {
    private Future<List<StatementModel>> statementModels;

    @Inject
    public StatementModelProvider(Configuration configuration, StatementParser statementParser,
                                  StatementModelConverter converter, ExecutorService executor) {
        statementModels = executor.submit(() -> {
            List<Statements> result = new ArrayList<>();
            for (String path : configuration.getStatementPaths()) {
                String text = new String(Files.readAllBytes(FileUtils.getFileFromString(path).toPath()), "windows-1251");
                List<Statements> statements = statementParser.parse(text);
                result.addAll(statements);
            }
            return result.stream().map(converter::convert).collect(Collectors.toList());
        });
    }

    public List<StatementModel> getStatementModels() {
        try {
            return statementModels.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while loading statements files", e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный путь до файлов выписок", ButtonType.OK);
            alert.showAndWait();
            return new ArrayList<>();
        }
    }
}
