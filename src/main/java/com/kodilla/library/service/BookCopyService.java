package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Status;
import com.kodilla.library.domain.dto.BookAndCopyDto;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.domain.dto.BookStatusDto;
import com.kodilla.library.domain.exception.book.EntityNotFoundException;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.simple.BookAndCopy;
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

    public List<BookAndCopyDto> getAll(String title) {
        List<Book> books = bookRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));

        List<BookAndCopy> copies = getCopies(books);
        copies.forEach(i -> i.setBookCopies(bookCopyRepository.findByBook(i.getBook())));

        return bookMapper.mapToBookAndCopyDtoList(copies);
    }

    public List<BookAndCopyDto> getAvailable(String title) {
        List<Book> books = bookRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));

        List<BookAndCopy> copies = getCopies(books);
        copies.forEach(i -> i.setBookCopies(bookCopyRepository.findByBookAndStatus(i.getBook(), Status.READY)));

        return bookMapper.mapToBookAndCopyDtoList(copies);
    }

    private List<BookAndCopy> getCopies(List<Book> books) {
        return books.stream()
                .map(BookAndCopy::new)
                .collect(Collectors.toList());
    }

    public BookCopyDto save(BookDto bookDto) {
        Book newCopy = bookRepository.findByTitleAndPublished(bookDto.getTitle(), bookDto.getPublished())
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.BOOK_ERROR, bookDto.getTitle())));

        return bookCopyMapper.mapToBookCopyDto(bookCopyRepository.save(new BookCopy(newCopy, Status.READY)));
     }

    public BookCopyDto update(BookStatusDto bookStatusDto) {
        Long id = bookStatusDto.getId();
        String status = bookStatusDto.getStatus();
        Status newStatus = Status.getStatus(status);

        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.COPY_ERROR, Long.toString(id))));

        bookCopy.setStatus(newStatus);
        bookCopyRepository.save(bookCopy);

        return bookCopyMapper.mapToBookCopyDto(bookCopy);
    }
}
