package com.example.excelparser.service.impl;

import com.example.excelparser.model.ExcelSheet;
import com.example.excelparser.repository.ExcelSheetRepository;
import com.example.excelparser.service.ExcelSheetService;
import org.springframework.stereotype.Service;

@Service
public class ExcelSheetServiceImpl implements ExcelSheetService {
    private final ExcelSheetRepository sheetRepository;

    public ExcelSheetServiceImpl(ExcelSheetRepository sheetRepository) {
        this.sheetRepository = sheetRepository;
    }

    @Override
    public ExcelSheet save(ExcelSheet excelSheet) {
        return sheetRepository.save(excelSheet);
    }

    @Override
    public boolean existsByName(String name) {
        return sheetRepository.existsByName(name);
    }
}
