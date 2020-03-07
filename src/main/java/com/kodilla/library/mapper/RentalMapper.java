package com.kodilla.library.mapper;

import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.dto.RentalDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {
    public RentalDto mapToRentalDto(Rental rental) {
        return new RentalDto(rental.getId(), rental.getBookCopy(), rental.getReader(), rental.getRentDate(), rental.getReturnDate());
    }

    public List<RentalDto> mapToRentalDtoList(List<Rental> rentals) {
        return rentals.stream()
                .map(i -> new RentalDto(i.getId(), i.getBookCopy(), i.getReader(), i.getRentDate(), i.getReturnDate()))
                .collect(Collectors.toList());
    }
}
