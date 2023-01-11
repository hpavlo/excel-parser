package com.example.excelparser.service;

import com.example.excelparser.model.ExcelCell;

public interface ExcelCellService {
    ExcelCell save(ExcelCell excelCell);

    Long getCellIdByParams(String bookName,
                           String sheetName,
                           int rowIndex,
                           int columnIndex,
                           String data);
}
