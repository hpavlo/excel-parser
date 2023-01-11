package com.example.excelparser.repository;

import com.example.excelparser.model.ExcelCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExcelCellRepository extends JpaRepository<ExcelCell, Long> {
    @Query("select c.id "
            + "from ExcelBook b join b.sheets s join s.rows r join r.cells c "
            + "where b.name = ?1 and s.name = ?2 and r.rowIndex = ?3 "
            + "and c.columnIndex = ?4 and c.data = ?5")
    Long getCellIdByParams(String bookName,
                           String sheetName,
                           int rowIndex,
                           int columnIndex,
                           String data);
}
