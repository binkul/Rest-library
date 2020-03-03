package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookStatusDto;
import com.kodilla.library.domain.simple.BookStatus;
import org.springframework.stereotype.Component;

@Component
public class BookCopyMapper {

    public BookCopyDto mapToBookCopyDto(BookCopy bookCopy) {
        return new BookCopyDto(bookCopy.getId(), bookCopy.getBook().getId(), bookCopy.getStatus());
    }

    public BookStatus mapToBookStatus(BookStatusDto bookStatusDto) {
        return new BookStatus(bookStatusDto.getId(), bookStatusDto.getStatus());
    }

}
