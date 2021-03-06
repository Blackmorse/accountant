package com.blackmorse.xls;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@Getter
public class DocumentReference {
    private File file;
    private List<String> sheetNames;
}
