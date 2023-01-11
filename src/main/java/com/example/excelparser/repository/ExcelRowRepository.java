package com.example.excelparser.repository;

import com.example.excelparser.model.ExcelRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelRowRepository extends JpaRepository<ExcelRow, Long> {
}
