package com.example.excelparser.repository;

import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.SearchData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExcelBookRepository extends JpaRepository<ExcelBook, Long> {
    List<ExcelBook> getAllByName(String name);

    boolean existsByName(String name);

    Optional<ExcelBook> getFirstByNameOrderByTimeHistoryDesc(String name);

    @Query("select new com.example.excelparser.model.SearchData("
            + "b.id, b.name, b.timeHistory, s.name, r.rowIndex, c.columnIndex, c.data) "
            + "from ExcelBook b join b.sheets s join s.rows r join r.cells c "
            + "where c.data like ?1")
    List<SearchData> searchAllByPattern(String pattern);
}
