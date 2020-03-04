package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("getBooks")
    List<BookDto> getBooks() {
        return  bookService.getAllBooks();
    }

    @GetMapping("getBookTitle/{title}")
    List<BookDto> getBook(@PathVariable String title) {
        return bookService.getAllBookByTitle(title);
    }

    @GetMapping("getBook/{id}")
    BookDto getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("getBookTitle/{title}/{published}")
    BookDto getBook(@PathVariable String title, @PathVariable int published) {
        return bookService.getBookByTitleAndYear(title, published);
    }

    @PostMapping(value = "addBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    String addBook(@RequestBody BookDto bookDto) {
        return bookService.saveBook(bookDto);
    }
}
