package com.example.excelparser.service.impl;

import com.example.excelparser.model.ExcelRow;
import com.example.excelparser.repository.ExcelRowRepository;
import com.example.excelparser.service.ExcelRowService;
import org.springframework.stereotype.Service;

@Service
public class ExcelRowServiceImpl implements ExcelRowService {
    private final ExcelRowRepository rowRepository;

    public ExcelRowServiceImpl(ExcelRowRepository rowRepository) {
        this.rowRepository = rowRepository;
    }

    @Override
    public ExcelRow save(ExcelRow excelRow) {
        return rowRepository.save(excelRow);
    }
}
