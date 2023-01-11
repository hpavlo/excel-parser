package com.example.excelparser.service;

import com.example.excelparser.model.ExcelBook;
import java.util.Optional;

public interface FileParser {
    Optional<ExcelBook> parse(String filePath);
}
