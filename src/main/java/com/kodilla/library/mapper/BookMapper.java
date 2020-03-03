package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.domain.dto.BookWithCopyDto;
import com.kodilla.library.domain.simple.BookWithCopy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public BookDto mapToBookDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPublished());
    }

    public Book mapToBook(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getPublished());
    }

    public List<BookDto> mapToBookDtoList(List<Book> books) {
        return books.stream()
                .map(i -> new BookDto(i.getId(), i.getTitle(), i.getAuthor(), i.getPublished()))
                .collect(Collectors.toList());
    }

    public List<BookWithCopyDto> mapToBookWithCopyDtoList(List<BookWithCopy> books) {
        return books.stream()
                .map(i -> new BookWithCopyDto(i.getId(), i.getTitle(), i.getAuthor(),  i.getPublished(), i.getCopies().size()))
                .collect(Collectors.toList());
    }
}
