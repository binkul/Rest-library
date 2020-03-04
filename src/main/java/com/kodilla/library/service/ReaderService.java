package com.kodilla.library.service;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.dto.ReaderDto;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.exception.book.RecordNotFoundException;
import com.kodilla.library.mapper.ReaderMapper;
import com.kodilla.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private ReaderMapper readerMapper;

    public ReaderDto getReader(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.READER_ERROR, Long.toString(id), "")));
        return readerMapper.mapToReaderDto(reader);
    }

    public List<ReaderDto> getReaders() {
        return readerMapper.mapToReaderDtoList(readerRepository.findAll());
    }

    public void addReader(ReaderDto readerDto) {
        readerRepository.save(readerMapper.mapToNewReader(readerDto));
    }

    public ReaderDto getReader(String name, String lastName) {
        Reader reader = readerRepository.findByNameAndLastName(name, lastName)
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.READER_ERROR, name, lastName)));
        return readerMapper.mapToReaderDto(reader);
    }
}
