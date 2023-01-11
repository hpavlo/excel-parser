package com.example.excelparser.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileHistoryResponse {
    private Long id;
    private String name;
    private LocalDateTime timeHistory;
}
