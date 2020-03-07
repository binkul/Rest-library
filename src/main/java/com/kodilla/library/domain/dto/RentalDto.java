package com.kodilla.library.domain.dto;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Reader;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RentalDto {
    private Long id;
    private BookCopy bookCopy;
    private Reader reader;
    private LocalDate rentDate;
    private LocalDate returnDate;
}
