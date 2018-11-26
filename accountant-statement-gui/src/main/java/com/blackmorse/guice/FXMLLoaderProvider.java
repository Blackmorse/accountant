package com.blackmorse.guice;

import javafx.fxml.FXMLLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FXMLLoaderProvider {
    private final FXMLLoader fxmlLoader;

    @Inject
    public FXMLLoaderProvider(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public FXMLLoader get() {
        FXMLLoader result = new FXMLLoader();
        result.setControllerFactory(fxmlLoader.getControllerFactory());
        return result;
    }
}
