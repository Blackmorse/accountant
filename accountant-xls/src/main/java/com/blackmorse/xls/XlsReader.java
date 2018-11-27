package com.blackmorse.xls;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsReader {
    private final String filePath;

    public XlsReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getSheetNames() throws IOException{
        List<String> result = new ArrayList<>();
        try (HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(filePath))){
            Iterator<Sheet> sheetIterator = book.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                result.add(sheet.getSheetName());
            }
        }
        return result;
    }
}
