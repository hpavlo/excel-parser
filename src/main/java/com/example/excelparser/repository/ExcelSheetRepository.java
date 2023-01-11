package com.example.excelparser.repository;

import com.example.excelparser.model.ExcelSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelSheetRepository extends JpaRepository<ExcelSheet, Long> {
    boolean existsByName(String name);
}
