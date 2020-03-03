package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Status;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.exception.book.RecordNotFoundException;
import com.kodilla.library.domain.simple.BookStatus;
import com.kodilla.library.domain.simple.BookWithCopy;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookCopyService {
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<BookWithCopy> getBookCopies(String title) {
        List<Book> books = bookRepository.findByTitle(title).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));

        return books.stream()
                .map(i -> new BookWithCopy(i.getId(), i.getTitle(), i.getAuthor(), i.getPublished(), bookCopyRepository.findByBook(i)))
                .collect(Collectors.toList());
    }

    public List<BookWithCopy> getAvailableCopy(String title) {
        List<Book> books = bookRepository.findByTitle(title).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));

        return books.stream()
                .map(i -> new BookWithCopy(i.getId(), i.getTitle(), i.getAuthor(), i.getPublished(), bookCopyRepository.findByBookAndStatus(i, Status.READY)))
                .collect(Collectors.toList());
    }

    public String addBookCopy(Book book) {
        Book newCopy = bookRepository.findByTitleAndPublished(book.getTitle(), book.getPublished()).orElse(null);

        if (newCopy != null) {
            bookCopyRepository.save(new BookCopy(newCopy, Status.READY));
            return "A copy of the book: " + book.getTitle() + " was successfully added to library database.";
        } else {
            return "A book: " + book.getTitle() + " does not exists in library database.";
        }
    }

    public BookCopy updateStatus(BookStatus bookStatus) {
        Long id = bookStatus.getId();
        String status = bookStatus.getStatus();

        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.COPY_ERROR, Long.toString(id))));
        Status newStatus = Status.getStatus(status);

        bookCopy.setStatus(newStatus);
        bookCopyRepository.save(bookCopy);
        return bookCopy;
    }
}
