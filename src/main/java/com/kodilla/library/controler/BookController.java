package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    List<BookDto> getBooks() {
        return  bookService.getAll();
    }

    @GetMapping
    List<BookDto> getBook(@RequestParam(required = true) String title) {
        return bookService.getAll(title);
    }

    @GetMapping("/{id}")
    BookDto getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("specify")
    BookDto getBook(@RequestParam(required = true) String title, @RequestParam int published) {
        return bookService.getBook(title, published);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    BookDto create(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }
}
