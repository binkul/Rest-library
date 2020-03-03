package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.BookCopyDto;
import com.kodilla.library.domain.dto.BookDto;
import com.kodilla.library.domain.dto.BookStatusDto;
import com.kodilla.library.domain.dto.BookWithCopyDto;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.service.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class BookCopyController {
    @Autowired
    private BookCopyService bookCopyService;
    @Autowired
    private BookCopyMapper bookCopyMapper;
    @Autowired
    private BookMapper bookMapper;

    @GetMapping("getAllCopies/{title}")
    List<BookWithCopyDto> getAllCopies(@PathVariable String title) {
        return bookMapper.mapToBookWithCopyDtoList(bookCopyService.getBookCopies(title));
    }

    @GetMapping("getAvailable/{title}")
    List<BookWithCopyDto> getAvailable(@PathVariable String title) {
        return bookMapper.mapToBookWithCopyDtoList(bookCopyService.getAvailableCopy(title));
    }

    @PostMapping(value = "addBookCopy", consumes = MediaType.APPLICATION_JSON_VALUE)
    String addBookCopy(@RequestBody BookDto bookDto) {
        return bookCopyService.addBookCopy(bookMapper.mapToBook(bookDto));
    }

    @PutMapping(value = "updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    BookCopyDto updateStatus(@RequestBody BookStatusDto bookStatusDto) {
        return bookCopyMapper.mapToBookCopyDto(bookCopyService.updateStatus(bookCopyMapper.mapToBookStatus(bookStatusDto)));
    }
}
