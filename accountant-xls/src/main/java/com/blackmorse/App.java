package com.blackmorse;


import com.blackmorse.model.StatementModel;
import com.blackmorse.xls.DocumentReference;
import com.blackmorse.xls.writer.XlsWriter;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        String path = "C:\\dev\\tst\\finrazbl\\2018\\р 11-18.xls";
////
//        XlsReader parser = new XlsReader(new File(path));
//
//        System.out.println(parser.getLastRowNumber("ко"));
//        System.out.println(parser.parseDocumentThemes());

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
