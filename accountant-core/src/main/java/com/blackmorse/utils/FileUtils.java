package com.blackmorse.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class FileUtils {
    private FileUtils(){}

    /**
     * Распознает это локальный адресс файла, или удаленный
     * и возвращает объект {@link java.io.File}
     */
    public static File getFileFromString(String path) throws URISyntaxException {
        if (path.startsWith("file:")) {
            return new File(new URI(path));
        } else {
            return new File(path);
        }
    }
}
