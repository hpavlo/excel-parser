package com.example.excelparser.service.impl;

import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.ExcelCell;
import com.example.excelparser.model.ExcelRow;
import com.example.excelparser.model.ExcelSheet;
import com.example.excelparser.model.SearchData;
import com.example.excelparser.service.ExcelBookService;
import com.example.excelparser.service.ExcelCellService;
import com.example.excelparser.service.ExcelRowService;
import com.example.excelparser.service.ExcelSheetService;
import com.example.excelparser.service.FileParser;
import com.example.excelparser.service.FileService;
import com.example.excelparser.service.PdfCreator;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private static final File UPLOADS_FOLDER = new File("uploads");
    private static final String[] VALID_FILE_FORMATS = {".xlsx", ".xls"};
    private final FileParser fileParser;
    private final PdfCreator pdfCreator;
    private final ExcelBookService bookService;
    private final ExcelSheetService sheetService;
    private final ExcelRowService rowService;
    private final ExcelCellService cellService;

    public FileServiceImpl(FileParser fileParser,
                           PdfCreator pdfCreator,
                           ExcelBookService bookService,
                           ExcelSheetService sheetService,
                           ExcelRowService rowService,
                           ExcelCellService cellService) {
        this.fileParser = fileParser;
        this.pdfCreator = pdfCreator;
        this.bookService = bookService;
        this.sheetService = sheetService;
        this.rowService = rowService;
        this.cellService = cellService;
    }

    @PostConstruct
    public void createTempFolderForUploadFiles() {
        UPLOADS_FOLDER.mkdir();
    }

    @PreDestroy
    public void deleteTempFolderForUploadFiles() {
        File[] existFiles = UPLOADS_FOLDER.listFiles();
        if (existFiles != null) {
            for (File file : existFiles) {
                file.delete();
            }
        }
        UPLOADS_FOLDER.delete();
    }

    @Override
    public Optional<String> upload(MultipartFile file) {
        if (file.isEmpty()) {
            return Optional.empty();
        }
        try {
            String filePath = UPLOADS_FOLDER.getAbsolutePath()
                    + "/" + new Date().getTime() + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            return Optional.of(filePath);
        } catch (IOException e) {
            log.error("Can't save file {}", file.getOriginalFilename(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<ExcelBook> getHistory(String name) {
        return bookService.getHistory(name);
    }

    @Override
    public void createPdfByName(String fileName, HttpServletResponse response) {
        pdfCreator.createPdfFileByName(fileName, response);
    }

    @Override
    public void createPdfById(Long id, HttpServletResponse response) {
        pdfCreator.createPdfFileById(id, response);
    }

    @Override
    public boolean valid(String filePath) {
        for (String validFileFormat : VALID_FILE_FORMATS) {
            if (filePath.endsWith(validFileFormat)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parse(String filePath, String originalFileName) {
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        Optional<ExcelBook> excelBookOptional = fileParser.parse(filePath);
        if (excelBookOptional.isEmpty()) {
            return false;
        }
        ExcelBook excelBook = excelBookOptional.get();
        excelBook.setName(fileName);
        if (bookService.existsByName(fileName)) {
            saveNewVersionOfBook(excelBook);
        } else {
            saveNewBook(excelBook);
        }
        return true;
    }

    @Override
    public List<SearchData> search(String text) {
        return bookService.searchData(text);
    }

    private void saveNewBook(ExcelBook book) {
        book.getSheets().forEach(this::saveSheet);
        bookService.save(book);
    }

    private void saveNewVersionOfBook(ExcelBook book) {
        for (ExcelSheet sheet : book.getSheets()) {
            if (sheetService.existsByName(sheet.getName())) {
                for (ExcelRow row : sheet.getRows()) {
                    saveRows(row, book.getName(), sheet.getName());
                }
                sheetService.save(sheet);
            } else {
                saveSheet(sheet);
            }
        }
        bookService.save(book);
    }

    private void saveSheet(ExcelSheet sheet) {
        for (ExcelRow row : sheet.getRows()) {
            for (ExcelCell cell : row.getCells()) {
                cellService.save(cell);
            }
            rowService.save(row);
        }
        sheetService.save(sheet);
    }

    private void saveRows(ExcelRow row, String bookName, String sheetName) {
        List<ExcelCell> cells = row.getCells();
        for (ExcelCell cell : cells) {
            Long cellId = cellService.getCellIdByParams(
                    bookName,
                    sheetName,
                    row.getRowIndex(),
                    cell.getColumnIndex(),
                    cell.getData());
            if (cellId == null) {
                cellService.save(cell);
            } else {
                cell.setId(cellId);
            }
        }
        rowService.save(row);
    }
}
