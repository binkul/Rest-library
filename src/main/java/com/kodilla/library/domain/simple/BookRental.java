package com.kodilla.library.domain.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookRental {
    private Long id;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private String name;
    private String lastName;
    private String title;
    private String status;
}
