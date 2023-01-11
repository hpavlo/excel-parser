package com.example.excelparser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellType;

@Getter
@Setter
@Entity
@Table(name = "excel_cells")
public class ExcelCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "column_index")
    private int columnIndex;
    private String data;
    @Enumerated
    private CellType type;
}
