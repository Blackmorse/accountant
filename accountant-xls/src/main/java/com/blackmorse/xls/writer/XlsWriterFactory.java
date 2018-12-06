package com.blackmorse.xls.writer;

import com.blackmorse.xls.DocumentReference;

public interface XlsWriterFactory {
    XlsWriter createXlsWriter(DocumentReference document);
}
