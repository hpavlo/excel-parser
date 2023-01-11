package com.example.excelparser.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "excel_rows")
public class ExcelRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "row_index")
    private int rowIndex;
    @Column(name = "last_cell_index")
    private int lastCellIndex;
    @ManyToMany
    private List<ExcelCell> cells;
}
