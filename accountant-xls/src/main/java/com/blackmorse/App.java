package com.blackmorse;


import com.blackmorse.xls.reader.DirectoryXlsReader;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        String path = "D:\\accountant config\\2017";

        DirectoryXlsReader reader = new DirectoryXlsReader(new File(path));
        System.out.println(reader.readThemesStatisticsFromDirectory(null));
        ////
//        XlsReader parser = new XlsReader(new File(path));
//        System.out.println(parser.getThemesStatistics());
//
//        System.out.println(parser.getLastRowNumber("ко"));
//        System.out.println(parser.parseDocumentSheets());

//        XlsWriter writer = new XlsWriter(new DocumentReference(new File(path), null), XlsWriter.Type.OUTCOME);
//
//        StatementModel model = new StatementModel();
//        model.setDate(new Date());
//        model.setSum(666.33d);
//        model.setPayer("Payer");
//        model.setReceiver("Receiver");
//
//        writer.writeStatement(model, "theme", "ко");
    }
}
