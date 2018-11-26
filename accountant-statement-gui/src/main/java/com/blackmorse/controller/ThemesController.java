package com.blackmorse.controller;

import com.blackmorse.configuration.Configuration;
import com.google.inject.Inject;

public class ThemesController {

    @Inject
    public ThemesController(Configuration configuration) {
        System.out.println();
    }
}
