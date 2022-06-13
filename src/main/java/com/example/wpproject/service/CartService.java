package com.example.wpproject.service;

import com.example.wpproject.model.Book;
import com.example.wpproject.model.Cart;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CartService {
    List<Book> listAllBooksInCart(Long id);
    List<Book> listAll();
    Cart getActiveCart(String username);
    Cart addBookToCart(String username, Long bookId);
    void deleteById(Long id);
    void writeTableHeader(PdfPTable table);

    void writeTableData(PdfPTable table);

    void export(HttpServletResponse response) throws DocumentException, IOException;

    Optional<Book> findById(Long id);

}
