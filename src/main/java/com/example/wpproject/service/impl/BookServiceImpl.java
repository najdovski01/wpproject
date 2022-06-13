package com.example.wpproject.service.impl;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.Book;
import com.example.wpproject.model.Genre;
import com.example.wpproject.model.PublishHouse;
import com.example.wpproject.model.exceptions.AuthorNotFoundException;
import com.example.wpproject.model.exceptions.BookNotFoundException;
import com.example.wpproject.model.exceptions.GenreNotFoundException;
import com.example.wpproject.model.exceptions.PublishHouseNotFoundException;
import com.example.wpproject.repository.AuthorRepository;
import com.example.wpproject.repository.BookRepository;
import com.example.wpproject.repository.GenreRepository;
import com.example.wpproject.repository.PublishHouseRepository;
import com.example.wpproject.service.BookService;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;



@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private List<Book> books;
    @Autowired
    private final PublishHouseRepository publishHouseRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, PublishHouseRepository publishHouseRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.publishHouseRepository = publishHouseRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.bookRepository.findByName(name);
    }

    @Override
    public Optional<Book> save(String name, Long authorId, Long genreId, String description, Double price, Long id) {
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFoundException(genreId));
        PublishHouse publishHouse = this.publishHouseRepository.findById(id).orElseThrow(() -> new PublishHouseNotFoundException(id));
        Book book = new Book(name, author, genre, description, price, publishHouse);
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    @Transactional
    public Optional<Book> edit(Long id, String name, Long authorId, Long genreId, String description, Double price, Long houseId) {
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFoundException(genreId));
        PublishHouse publishHouse = this.publishHouseRepository.findById(houseId).orElseThrow(() -> new PublishHouseNotFoundException(houseId));
        Book book = this.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setDescription(description);
        book.setPrice(price);
        book.setPublishHouse(publishHouse);
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
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

        cell.setPhrase(new Phrase("Book", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Author", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Genre", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Publish House", font));
        table.addCell(cell);
    }



    public void export(HttpServletResponse response, @PathVariable Long id) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Book Info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);

        table.addCell(this.bookRepository.findById(id).get().getName());
        table.addCell(this.bookRepository.findById(id).get().getAuthor().getName());
        table.addCell(this.bookRepository.findById(id).get().getDescription());
        table.addCell(this.bookRepository.findById(id).get().getGenre().getName());
        table.addCell(this.bookRepository.findById(id).get().getPublishHouse().getName());
        document.add(table);

        document.close();

    }

}
