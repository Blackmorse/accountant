package com.blackmorse;


import com.blackmorse.xls.reader.DirectoryXlsReader;
import com.blackmorse.xls.writer.themes.ThemesWriter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        String path = "C:\\tmp\\acc\\test.xls";
        File file = new File(path);

        ThemesWriter writer = new ThemesWriter();
        writer.writeFile(file, Arrays.asList("theme1", "2","count"));
    }
}
