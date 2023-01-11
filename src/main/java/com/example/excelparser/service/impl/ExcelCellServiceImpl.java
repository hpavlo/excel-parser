package com.example.excelparser.service.impl;

import com.example.excelparser.model.ExcelCell;
import com.example.excelparser.repository.ExcelCellRepository;
import com.example.excelparser.service.ExcelCellService;
import org.springframework.stereotype.Service;

@Service
public class ExcelCellServiceImpl implements ExcelCellService {
    private final ExcelCellRepository cellRepository;

    public ExcelCellServiceImpl(ExcelCellRepository cellRepository) {
        this.cellRepository = cellRepository;
    }

    @Override
    public ExcelCell save(ExcelCell excelCell) {
        return cellRepository.save(excelCell);
    }

    @Override
    public Long getCellIdByParams(String bookName,
                                  String sheetName,
                                  int rowIndex,
                                  int columnIndex,
                                  String data) {
        return cellRepository.getCellIdByParams(bookName, sheetName, rowIndex, columnIndex, data);
    }
}
