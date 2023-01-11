package com.example.excelparser.service;

import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.SearchData;
import java.util.List;
import java.util.Optional;

public interface ExcelBookService {
    ExcelBook save(ExcelBook excelBook);

    List<ExcelBook> getHistory(String name);

    ExcelBook get(Long id);

    boolean existsByName(String name);

    Optional<ExcelBook> getLastVersionByName(String name);

    List<SearchData> searchData(String text);
}
