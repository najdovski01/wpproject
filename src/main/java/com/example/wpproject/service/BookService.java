package com.example.wpproject.service;

import com.example.wpproject.model.Book;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> findByName(String name);
    Optional<Book> save(String name, Long authorId, Long genreId, String description, Double price, Long id);
    Optional<Book> edit(Long id, String name, Long authorId, Long genreId, String description, Double price, Long houseId);
    void deleteById(Long id);

    void export(HttpServletResponse response, Long id) throws DocumentException, IOException;

    void writeTableHeader(PdfPTable table);

    //void writeTableData(PdfPTable table, @PathVariable Long id);
}
