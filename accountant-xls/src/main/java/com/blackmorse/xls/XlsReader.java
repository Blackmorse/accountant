package com.blackmorse.xls;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsReader {
    private final File file;

    public XlsReader(File file) {
        this.file = file;
    }

    public Document parseDocument() throws IOException{
        List<String> result = new ArrayList<>();
        try (HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file))){
            Iterator<Sheet> sheetIterator = book.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                result.add(sheet.getSheetName());
            }
        }
        return new Document(file, result);
    }
}
