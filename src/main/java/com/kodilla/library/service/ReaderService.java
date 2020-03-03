package com.kodilla.library.service;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.exception.book.RecordNotFoundException;
import com.kodilla.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    public Reader getReader(Long id) {
        return readerRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.READER_ERROR, Long.toString(id), "")));
    }

    public List<Reader> getReaders() {
        return readerRepository.findAll();
    }

    public void addReader(Reader reader) {
        readerRepository.save(reader);
    }

    public Reader getReader(String name, String lastName) {
        return readerRepository.findByNameAndLastName(name, lastName).orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.READER_ERROR, name, lastName)));
    }
}
