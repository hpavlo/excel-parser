package com.example.excelparser.dto.mapper;

import com.example.excelparser.dto.response.SearchDataResponse;
import com.example.excelparser.model.SearchData;
import org.springframework.stereotype.Component;

@Component
public class SearchDataMapper {
    public SearchDataResponse toDto(SearchData searchData) {
        return new SearchDataResponse(
                searchData.getBookId(),
                searchData.getBookName(),
                searchData.getTimeHistory(),
                searchData.getSheetName(),
                searchData.getRowIndex(),
                searchData.getColumnIndex(),
                searchData.getData()
        );
    }
}
