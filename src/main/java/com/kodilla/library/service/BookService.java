package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.exception.book.EntityNotFoundException;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    public List<BookDto> getAll() {
        return bookMapper.mapToBookDtoList(bookRepository.findAll());
    }

    public List<BookDto> getAll(String title) {
        List<Book> books = bookRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));
        return bookMapper.mapToBookDtoList(books);
    }

    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.BOOK_ERROR, Long.toString(id))));
        return bookMapper.mapToBookDto(book);
    }

    public BookDto getBook(String title, int published) {
        Book book = bookRepository.findByTitleAndPublished(title, published)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));
        return bookMapper.mapToBookDto(book);
    }

    public BookDto save(BookDto bookDto) {
        Book book = bookMapper.mapToBook(bookDto);
        boolean bookExist = bookRepository.existsBookByTitleAndAuthorAndPublished(book.getTitle(), book.getAuthor(), book.getPublished());

        if (!bookExist) {
            book = bookRepository.save(book);
        }

        return bookMapper.mapToBookDto(book);
    }
}
