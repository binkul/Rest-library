package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.BookOrderDto;
import com.kodilla.library.domain.dto.BookReturnDto;
import com.kodilla.library.domain.dto.RentalDto;
import com.kodilla.library.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/rent")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping
    List<RentalDto> getRentals() {
        return rentalService.getAll();
    }

    @GetMapping("/{id}")
    RentalDto getRental(@PathVariable Long id) {
        return rentalService.getRental(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    RentalDto create(@RequestBody BookOrderDto bookOrderDto) {
        return rentalService.save(bookOrderDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    RentalDto update(@RequestBody BookReturnDto bookReturnDto) {
        return rentalService.update(bookReturnDto);
    }
}
