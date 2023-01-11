package com.example.excelparser.service;

import javax.servlet.http.HttpServletResponse;

public interface PdfCreator {
    void createPdfFileByName(String fileName, HttpServletResponse response);

    void createPdfFileById(Long id, HttpServletResponse response);
}
