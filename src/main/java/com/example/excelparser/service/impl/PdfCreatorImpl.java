package com.example.excelparser.service.impl;

import com.example.excelparser.model.ExcelBook;
import com.example.excelparser.model.ExcelRow;
import com.example.excelparser.model.ExcelSheet;
import com.example.excelparser.service.ExcelBookService;
import com.example.excelparser.service.PdfCreator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class PdfCreatorImpl implements PdfCreator {
    private final ExcelBookService bookService;

    public PdfCreatorImpl(ExcelBookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void createPdfFileByName(String fileName, HttpServletResponse response) {
        Optional<ExcelBook> bookOptional = bookService.getLastVersionByName(fileName);
        bookOptional.ifPresent(excelBook -> createPdfFile(excelBook, response));
    }

    @Override
    public void createPdfFileById(Long id, HttpServletResponse response) {
        ExcelBook book = bookService.get(id);
        createPdfFile(book, response);
    }

    private void createPdfFile(ExcelBook book, HttpServletResponse response) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            addSheetsToDocument(document, book);
            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSheetsToDocument(Document document, ExcelBook book) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        for (ExcelSheet sheet : book.getSheets()) {
            if (sheet.getRows().size() == 0) {
                continue;
            }
            Chunk sheetName = new Chunk(sheet.getName(), font).setLineHeight(15);
            document.add(sheetName);
            int columnsNumber = sheet.getRows().stream()
                    .mapToInt(ExcelRow::getLastCellIndex)
                    .max().getAsInt() + 1;
            PdfPTable table = new PdfPTable(columnsNumber);
            table.setPaddingTop(5);
            for (ExcelRow row : sheet.getRows()) {
                for (int i = 0, j = 0; i < columnsNumber; i++) {
                    if (j < row.getCells().size() && i == row.getCells().get(j).getColumnIndex()) {
                        table.addCell(row.getCells().get(j).getData());
                        j++;
                    } else {
                        table.addCell("");
                    }
                }
            }
            document.add(table);
        }
    }
}
