package com.kodilla.library.controler;

import com.kodilla.library.domain.dto.ReaderDto;
import com.kodilla.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("getReaders")
    List<ReaderDto> getReaders() {
        return readerService.getReaders();
    }

    @GetMapping("getReader/{id}")
    ReaderDto getReader(@PathVariable Long id) {
        return readerService.getReader(id);
    }

    @GetMapping("getReader/{name}/{lastName}")
    ReaderDto getReader(@PathVariable String name, @PathVariable String lastName) {
        return readerService.getReader(name, lastName);
    }

    @PostMapping(value = "addReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    String addReader(@RequestBody ReaderDto readerDto) {
        readerService.addReader(readerDto);
        return "New reader: " + readerDto.getName() + " " + readerDto.getLastName() + " successfully added to library.";
    }

}
