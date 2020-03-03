package com.kodilla.library.mapper;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.dto.ReaderDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {
    public ReaderDto mapToReaderDto(Reader reader) {
        return new ReaderDto(reader.getId(), reader.getName(), reader.getLastName(), reader.getSigned());
    }

    public Reader mapToNewReader(ReaderDto readerDto) {
        return new Reader(readerDto.getName(), readerDto.getLastName(), LocalDate.now());
    }

    public List<ReaderDto> mapToReaderDtoList(List<Reader> readers) {
        return readers.stream()
                .map(i -> new ReaderDto(i.getId(), i.getName(), i.getLastName(), i.getSigned()))
                .collect(Collectors.toList());
    }

}
