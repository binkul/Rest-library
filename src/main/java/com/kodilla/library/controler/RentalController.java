package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.BookRentalDto;
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

    @GetMapping("getRentals")
    List<BookRentalDto> getRentals() {
        return rentalService.getRentals();
    }

    @PostMapping(value = "createRent", consumes = MediaType.APPLICATION_JSON_VALUE)
    BookRentalDto createRent(@RequestBody BookRentalDto bookRentalDto) {
        return rentalService.createRent(bookRentalDto);
    }

    @PutMapping(value = "returnBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    BookRentalDto returnBook(@RequestBody BookRentalDto bookRentalDto) {
        return rentalService.returnBook(bookRentalDto);
    }
}
