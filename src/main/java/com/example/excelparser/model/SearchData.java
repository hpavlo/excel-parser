package com.example.excelparser.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchData {
    private Long bookId;
    private String bookName;
    private LocalDateTime timeHistory;
    private String sheetName;
    private int rowIndex;
    private int columnIndex;
    private String data;
}
