package com.example.excelparser.dto.mapper;

import com.example.excelparser.dto.response.FileHistoryResponse;
import com.example.excelparser.model.ExcelBook;
import org.springframework.stereotype.Component;

@Component
public class FileHistoryMapper {
    public FileHistoryResponse toDto(ExcelBook book) {
        return new FileHistoryResponse(
                book.getId(),
                book.getName(),
                book.getTimeHistory());
    }
}
