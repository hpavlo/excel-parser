package com.example.excelparser.service.impl;

import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.ExcelCell;
import com.example.excelparser.model.ExcelRow;
import com.example.excelparser.model.ExcelSheet;
import com.example.excelparser.service.FileParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileParserImpl implements FileParser {
    private static final String XLSX_EXTENSION = ".xlsx";

    @Override
    public Optional<ExcelBook> parse(String filePath) {
        try {
            Workbook workbook = getWorkbook(filePath);
            ExcelBook excelBook = new ExcelBook();
            excelBook.setTimeHistory(LocalDateTime.now());
            List<ExcelSheet> excelSheets = new ArrayList<>(workbook.getNumberOfSheets());
            excelBook.setSheets(excelSheets);

            for (Sheet sheet : workbook) {
                ExcelSheet excelSheet = new ExcelSheet();
                excelSheet.setName(sheet.getSheetName());
                List<ExcelRow> excelRows = new ArrayList<>(sheet.getPhysicalNumberOfRows());
                excelSheet.setRows(excelRows);
                excelSheets.add(excelSheet);

                for (Row row : sheet) {
                    ExcelRow excelRow = new ExcelRow();
                    excelRow.setRowIndex(row.getRowNum());
                    List<ExcelCell> excelCells = new ArrayList<>(row.getPhysicalNumberOfCells());
                    excelRow.setCells(excelCells);
                    excelRows.add(excelRow);

                    for (Cell cell : row) {
                        ExcelCell excelCell = new ExcelCell();
                        excelCell.setColumnIndex(cell.getColumnIndex());
                        excelCell.setType(cell.getCellType());

                        switch (cell.getCellType()) {
                            case STRING:
                                excelCell.setData(getStringCellValue(cell));
                                break;
                            case NUMERIC:
                                excelCell.setData(getNumericCellValue(cell));
                                break;
                            case BOOLEAN:
                                excelCell.setData(getBooleanCellValue(cell));
                                break;
                            case FORMULA:
                                excelCell.setData(getFormulaCellValue(cell));
                                break;
                            default: continue;
                        }
                        excelRow.setLastCellIndex(excelCell.getColumnIndex());
                        excelCells.add(excelCell);
                    }
                }
            }
            return Optional.of(excelBook);
        } catch (IOException e) {
            log.error("Can't parse file " + filePath, e);
        }
        return Optional.empty();
    }

    private Workbook getWorkbook(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        if (filePath.endsWith(XLSX_EXTENSION)) {
            return new XSSFWorkbook(file);
        }
        return new HSSFWorkbook(file);
    }

    private String getStringCellValue(Cell cell) {
        return cell.getRichStringCellValue().getString();
    }

    private String getNumericCellValue(Cell cell) {
        if (DateUtil.isCellDateFormatted(cell)) {
            return String.valueOf(cell.getDateCellValue());
        }
        return String.valueOf(cell.getNumericCellValue());
    }

    private String getBooleanCellValue(Cell cell) {
        return String.valueOf(cell.getBooleanCellValue());
    }

    private String getFormulaCellValue(Cell cell) {
        return cell.getCellFormula();
    }
}
