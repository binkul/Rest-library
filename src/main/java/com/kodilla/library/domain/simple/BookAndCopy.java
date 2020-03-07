package com.kodilla.library.domain.simple;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAndCopy {
    private Book book;
    private List<BookCopy> bookCopies;

    public BookAndCopy(Book book) {
        this.book = book;
        this.bookCopies = new ArrayList<>();
    }
}
