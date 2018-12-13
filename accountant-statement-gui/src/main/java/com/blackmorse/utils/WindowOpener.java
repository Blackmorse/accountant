package com.blackmorse.utils;

import com.blackmorse.controller.MainController;
import com.blackmorse.guice.FXMLLoaderProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Slf4j
@Singleton
public class WindowOpener {
    private final FXMLLoaderProvider loaderProvider;

    @Inject
    public WindowOpener(FXMLLoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    public FXMLLoader openWindow(String fxmlPath, String title, Modality modality, Window owner) {
        Stage stage = new Stage();
        stage.setTitle(title);

        FXMLLoader loader = loaderProvider.get();
        Parent parent = null;
        try {
            parent = loader.load(
                    MainController.class.getResourceAsStream(fxmlPath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        if (owner != null) {
            stage.initOwner(owner);
        }
        stage.initModality(modality);

        log.info("{} window is open", title);
        stage.show();
        return loader;
    }
}
