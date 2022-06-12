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
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void export(HttpServletResponse response, Long id) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        String name = this.bookRepository.findById(id).get().getName();
        String author = this.bookRepository.findById(id).get().getAuthor().getName();
        String description = this.bookRepository.findById(id).get().getDescription();
        String genre = this.bookRepository.findById(id).get().getGenre().getName();
        String publishHouse = this.bookRepository.findById(id).get().getPublishHouse().getName();


        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("Book informations: ");
        Paragraph paragraph1 = new Paragraph("Book: " + name);
        Paragraph paragraph2 = new Paragraph("Auhor: " + author);
        Paragraph paragraph3 = new Paragraph("Description: " + description);
        Paragraph paragraph4 = new Paragraph("Genre: " + genre);
        Paragraph paragraph5 = new Paragraph("Publish House: " + publishHouse);

        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph3.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph4.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph5.setAlignment(Paragraph.ALIGN_LEFT);


        paragraph.setSpacingAfter(15);
        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);
        document.close();
    }
}
