package com.example.wpproject.service.impl;

import com.example.wpproject.model.Book;
import com.example.wpproject.model.Cart;
import com.example.wpproject.model.User;
import com.example.wpproject.model.enumerations.CartStatus;
import com.example.wpproject.model.exceptions.BookAlreadyInCartException;
import com.example.wpproject.model.exceptions.BookNotFoundException;
import com.example.wpproject.model.exceptions.CartNotFoundException;
import com.example.wpproject.model.exceptions.UserNotFoundException;
import com.example.wpproject.repository.BookRepository;
import com.example.wpproject.repository.CartRepository;
import com.example.wpproject.repository.UserRepository;
import com.example.wpproject.service.CartService;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private List<Book> books;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAllBooksInCart(Long id) {
        if(!this.cartRepository.findById(id).isPresent())
            throw new CartNotFoundException(id);
        return this.cartRepository.findById(id).get().getBooks();
    }

    @Override
    public List<Book> listAll() {
        return this.bookRepository.findAll();
    }


    @Override
    public Cart getActiveCart(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return this.cartRepository.findByUserAndStatus(user, CartStatus.CREATED)
                .orElseGet(() -> {
                    Cart cart = new Cart(user);
                    return this.cartRepository.save(cart);
                });
    }

    @Override
    public Cart addBookToCart(String username, Long bookId) {
        Cart cart = this.getActiveCart(username);
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if(cart.getBooks().stream().filter(b -> b.getId().equals(bookId)).collect(Collectors.toList()).size()>0){
            throw new BookAlreadyInCartException(bookId, username);
        }
        cart.getBooks().add(book);
        return this.cartRepository.save(cart);
    }
    @Override
    public void deleteById(Long id) {
        this.cartRepository.deleteById(id);
    }

    @Override
    public void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Book ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Book name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Author", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Genre", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Publish House", font));
        table.addCell(cell);
    }
    @Override
    public void writeTableData(PdfPTable table){
        for (Book book : books) {
            table.addCell(String.valueOf(book.getId()));
            table.addCell(book.getAuthor().getName());
            table.addCell(book.getDescription());
            table.addCell(book.getGenre().getName());
            table.addCell(book.getPublishHouse().getName());
        }
    }

    @Override
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        PdfPTable table = new PdfPTable(5);


        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of books in cart", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);

        document.close();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }
}
