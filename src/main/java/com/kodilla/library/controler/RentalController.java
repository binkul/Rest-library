package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.BookRentalDto;
import com.kodilla.library.mapper.RentalMapper;
import com.kodilla.library.service.BookCopyService;
import com.kodilla.library.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class RentalController {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private RentalMapper rentalMapper;

    @GetMapping("getRentals")
    List<BookRentalDto> getRentals() {
        return rentalMapper.mapToBookRentalDtoList(rentalService.getRentals());
    }

    @PostMapping(value = "createRent", consumes = MediaType.APPLICATION_JSON_VALUE)
    BookRentalDto createRent(@RequestBody BookRentalDto bookRentalDto) {
        return rentalMapper.mapToBookRentalDto(rentalService.createRent(rentalMapper.mapToBookRental(bookRentalDto)));
    }

    @PutMapping(value = "returnBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    BookRentalDto returnBook(@RequestBody BookRentalDto bookRentalDto) {
        return rentalMapper.mapToBookRentalDto(rentalService.returnBook(rentalMapper.mapToBookRental(bookRentalDto)));
    }
}
