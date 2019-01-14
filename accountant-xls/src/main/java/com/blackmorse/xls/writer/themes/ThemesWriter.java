package com.blackmorse.xls.writer.themes;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ThemesWriter {
    public void writeFile(File file, List<String> themes) {
        try(Workbook workbook = new HSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(file)) {

            for (String theme : themes) {
                workbook.createSheet(theme);
            }
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
