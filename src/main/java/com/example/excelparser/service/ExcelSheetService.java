package com.example.excelparser.service;

import com.example.excelparser.model.ExcelSheet;

public interface ExcelSheetService {
    ExcelSheet save(ExcelSheet excelSheet);

    boolean existsByName(String name);
}
