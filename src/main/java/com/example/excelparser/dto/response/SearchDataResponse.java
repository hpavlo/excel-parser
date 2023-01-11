package com.example.excelparser.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchDataResponse {
    private Long bookId;
    private String bookName;
    private LocalDateTime timeHistory;
    private String sheetName;
    private int rowIndex;
    private int columnIndex;
    private String data;
}
