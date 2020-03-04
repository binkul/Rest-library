package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Status;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.domain.dto.BookStatusDto;
import com.kodilla.library.domain.dto.BookWithCopyDto;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.exception.book.RecordNotFoundException;
import com.kodilla.library.domain.simple.BookWithCopy;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.mapper.BookMapper;
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
    @Autowired
    private BookCopyMapper bookCopyMapper;
    @Autowired
    private BookMapper bookMapper;

    public List<BookWithCopyDto> getBookCopies(String title) {
        List<Book> books = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));

        return bookMapper.mapToBookWithCopyDtoList(
                books.stream()
                    .map(this::findAllBooks)
                    .collect(Collectors.toList())
        );
    }

    public List<BookWithCopyDto> getAvailableCopy(String title) {
        List<Book> books = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));

        return bookMapper.mapToBookWithCopyDtoList(
                books.stream()
                    .map(this::findAvailableBooks)
                    .collect(Collectors.toList())
        );
    }

    public String addBookCopy(BookDto bookDto) {
        Book book = bookMapper.mapToBook(bookDto);
        Book newCopy = bookRepository.findByTitleAndPublished(book.getTitle(), book.getPublished()).orElse(null);

        if (newCopy != null) {
            bookCopyRepository.save(new BookCopy(newCopy, Status.READY));
            return "A copy of the book: " + book.getTitle() + " was successfully added to library database.";
        } else {
            return "A book: " + book.getTitle() + " does not exists in library database.";
        }
    }

    public BookCopyDto updateStatus(BookStatusDto bookStatusDto) {
        Long id = bookCopyMapper.mapToBookStatus(bookStatusDto).getId();
        String status = bookCopyMapper.mapToBookStatus(bookStatusDto).getStatus();
        Status newStatus = Status.getStatus(status);

        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.COPY_ERROR, Long.toString(id))));

        bookCopy.setStatus(newStatus);
        bookCopyRepository.save(bookCopy);

        return bookCopyMapper.mapToBookCopyDto(bookCopy);
    }

    private BookWithCopy findAllBooks(Book book) {
        return new BookWithCopy(book.getId(), book.getTitle(), book.getAuthor(), book.getPublished(), bookCopyRepository.findByBook(book));
    }

    private BookWithCopy findAvailableBooks(Book book) {
        return new BookWithCopy(book.getId(), book.getTitle(), book.getAuthor(), book.getPublished(), bookCopyRepository.findByBookAndStatus(book, Status.READY));
    }
}
