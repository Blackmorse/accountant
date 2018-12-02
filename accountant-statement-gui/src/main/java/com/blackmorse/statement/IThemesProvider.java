package com.blackmorse.statement;

import java.util.Set;
import java.util.concurrent.Future;

public interface IThemesProvider {
    Future<Set<String>> getThemes();
}
