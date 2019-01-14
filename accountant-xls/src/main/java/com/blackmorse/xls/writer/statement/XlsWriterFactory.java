package com.blackmorse.xls.writer.statement;

import com.blackmorse.xls.DocumentReference;

public interface XlsWriterFactory {
    XlsWriter createXlsWriter(DocumentReference document);
}
