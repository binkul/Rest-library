package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.exception.book.RecordNotFoundException;
import com.kodilla.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, Long.toString(id))));
    }

    public List<Book> getAllBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));
    }

    public Book getBookByTitleAndYear(String title, int published) {
        return bookRepository.findByTitleAndPublished(title, published).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));
    }

    public String saveBook(Book book) {
        boolean bookExist = bookRepository.existsBookByTitleAndAuthorAndPublished(book.getTitle(), book.getAuthor(), book.getPublished());

        if (bookExist) {
            return "Book exist, cannot be added to database.";
        } else {
            bookRepository.save(book);
            return "Book was added successfully to database.";
        }
    }
}
