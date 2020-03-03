package com.kodilla.library.mapper;

import com.kodilla.library.domain.dto.BookRentalDto;
import com.kodilla.library.domain.simple.BookRental;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public BookRental mapToBookRental(BookRentalDto bookRentalDto) {
        return new BookRental(bookRentalDto.getId(), bookRentalDto.getRentDate(), bookRentalDto.getReturnDate(),
                bookRentalDto.getName(), bookRentalDto.getLastName(), bookRentalDto.getTitle(), bookRentalDto.getStatus());
    }

    public BookRentalDto mapToBookRentalDto(BookRental bookRental) {
        return new BookRentalDto(bookRental.getId(), bookRental.getRentDate(), bookRental.getReturnDate(),
                bookRental.getName(), bookRental.getLastName(), bookRental.getTitle(), bookRental.getStatus());
    }

    public List<BookRentalDto> mapToBookRentalDtoList(List<BookRental> books) {
        return books.stream()
                .map(i -> new BookRentalDto(i.getId(), i.getRentDate(), i.getReturnDate(), i.getName(), i.getLastName(), i.getTitle(), i.getStatus()))
                .collect(Collectors.toList());
    }


}
