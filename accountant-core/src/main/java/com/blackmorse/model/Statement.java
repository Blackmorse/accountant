package com.blackmorse.model;

import java.util.HashMap;
import java.util.Map;

public class Statement {
    private Map<String, String> data = new HashMap<>();

    public void put(String key, String value) {
        data.put(key, value);
    }
}
