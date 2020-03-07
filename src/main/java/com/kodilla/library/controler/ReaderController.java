package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.ReaderDto;
import com.kodilla.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/reader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/all")
    List<ReaderDto> getReaders() {
        return readerService.getAll();
    }

    @GetMapping("/{id}")
    ReaderDto getReader(@PathVariable Long id) {
        return readerService.getReader(id);
    }

    @GetMapping
    ReaderDto getReader(@RequestParam(required = true) String name, @RequestParam(required = true) String lastName) {
        return readerService.getReader(name, lastName);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ReaderDto create(@RequestBody ReaderDto readerDto) {
        return readerService.save(readerDto);
    }
}
