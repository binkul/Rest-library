package com.kodilla.library.domain.dto;

import com.kodilla.library.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookAndCopyDto {
    private Book book;
    private int copies;
}
