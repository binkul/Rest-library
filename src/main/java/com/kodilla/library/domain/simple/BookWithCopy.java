package com.kodilla.library.domain.simple;

import com.kodilla.library.domain.BookCopy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookWithCopy {
    private Long id;
    private String title;
    private String author;
    private int published;
    private List<BookCopy> copies = new ArrayList<>();
}
