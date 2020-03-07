package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.BookAndCopyDto;
import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.domain.dto.BookStatusDto;
import com.kodilla.library.service.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/copy")
public class BookCopyController {
    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("/all")
    List<BookAndCopyDto> getAll(@RequestParam(required = true) String title) {
        return bookCopyService.getAll(title);
    }

    @GetMapping("/available")
    List<BookAndCopyDto> getAvailable(@RequestParam(required = true) String title) {
        return bookCopyService.getAvailable(title);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    BookCopyDto create(@RequestBody BookDto bookDto) {
        return bookCopyService.save(bookDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    BookCopyDto update(@RequestBody BookStatusDto bookStatusDto) {
        return bookCopyService.update(bookStatusDto);
    }
}
