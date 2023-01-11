package com.example.excelparser.controller;

import com.example.excelparser.dto.mapper.FileHistoryMapper;
import com.example.excelparser.dto.mapper.SearchDataMapper;
import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.SearchData;
import com.example.excelparser.service.ExcelBookService;
import com.example.excelparser.service.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    private final FileService fileService;
    private final ExcelBookService bookService;
    private final FileHistoryMapper fileHistoryMapper;
    private final SearchDataMapper searchDataMapper;

    public FileController(FileService fileService,
                          ExcelBookService bookService,
                          FileHistoryMapper fileHistoryMapper,
                          SearchDataMapper searchDataMapper) {
        this.fileService = fileService;
        this.bookService = bookService;
        this.fileHistoryMapper = fileHistoryMapper;
        this.searchDataMapper = searchDataMapper;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("Upload .xlsx/.xls file to DB")
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!fileService.valid(fileName)) {
            return ResponseEntity.badRequest().body("File " + fileName + " has incorrect format");
        }
        Optional<String> filePathOptional = fileService.upload(file);
        if (filePathOptional.isEmpty()) {
            return ResponseEntity.internalServerError().body("Can't upload file " + fileName);
        }
        if (!fileService.parse(filePathOptional.get(), fileName)) {
            return ResponseEntity.badRequest().body("File " + fileName + " has incorrect data");
        }
        return ResponseEntity.ok("File " + fileName + " was uploaded");
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("Get list of history books")
    public ResponseEntity<?> getHistory(
            @RequestParam @ApiParam("File name without extension") String name) {
        List<ExcelBook> books = fileService.getHistory(name);
        return ResponseEntity.ok(books
                .stream()
                .map(fileHistoryMapper::toDto)
                .toList());
    }

    @GetMapping("/pdf")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("Create pdf file of last version by name and download it")
    public void createPdf(@RequestParam @ApiParam("File (book) name without extension") String name,
                          HttpServletResponse response) {
        if (bookService.existsByName(name)) {
            setResponseHeaders(response, name);
            fileService.createPdfByName(name, response);
        }
    }

    @GetMapping("/pdf/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("Create pdf file of any version by id and download it")
    public void createPdf(@PathVariable @ApiParam("File (book) id") Long id,
                          HttpServletResponse response) {
        setResponseHeaders(response, id.toString());
        fileService.createPdfById(id, response);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("Search cells by text in all books")
    public ResponseEntity<?> search(@RequestParam @ApiParam("Search pattern") String text) {
        List<SearchData> searchDataList = fileService.search(text);
        return ResponseEntity.ok(searchDataList
                .stream()
                .map(searchDataMapper::toDto)
                .toList());
    }

    private void setResponseHeaders(HttpServletResponse response, String fileName) {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName + ".pdf";
        response.setHeader(headerKey, headerValue);
    }
}
