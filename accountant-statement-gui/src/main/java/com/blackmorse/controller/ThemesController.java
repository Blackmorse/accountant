package com.blackmorse.controller;

import com.blackmorse.configuration.Configuration;
import com.blackmorse.model.StatementModel;
import com.google.inject.Inject;

public class ThemesController {

    private StatementModel model;
    private String sheetName;
    @Inject
    public ThemesController(Configuration configuration) {

    }

    public void setData(StatementModel model, String sheetName) {
        this.model = model;
        this.sheetName = sheetName;
        System.out.println(model + sheetName);
    }
}
