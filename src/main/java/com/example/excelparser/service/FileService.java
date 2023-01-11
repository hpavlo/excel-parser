package com.example.excelparser.service;

import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.SearchData;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Optional<String> upload(MultipartFile file);

    List<ExcelBook> getHistory(String name);

    void createPdfByName(String fileName, HttpServletResponse response);

    void createPdfById(Long id, HttpServletResponse response);

    boolean valid(String filePath);

    boolean parse(String filePath, String originalFileName);

    List<SearchData> search(String text);
}
