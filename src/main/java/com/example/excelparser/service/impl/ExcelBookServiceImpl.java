package com.example.excelparser.service.impl;

import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.SearchData;
import com.example.excelparser.repository.ExcelBookRepository;
import com.example.excelparser.service.ExcelBookService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ExcelBookServiceImpl implements ExcelBookService {
    private final ExcelBookRepository bookRepository;

    public ExcelBookServiceImpl(ExcelBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public ExcelBook save(ExcelBook excelBook) {
        return bookRepository.save(excelBook);
    }

    @Override
    public List<ExcelBook> getHistory(String name) {
        return bookRepository.getAllByName(name);
    }

    @Override
    public ExcelBook get(Long id) {
        return bookRepository.getReferenceById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return bookRepository.existsByName(name);
    }

    @Override
    public Optional<ExcelBook> getLastVersionByName(String name) {
        return bookRepository.getFirstByNameOrderByTimeHistoryDesc(name);
    }

    @Override
    public List<SearchData> searchData(String text) {
        String pattern = "%" + text + "%";
        return bookRepository.searchAllByPattern(pattern);
    }
}
