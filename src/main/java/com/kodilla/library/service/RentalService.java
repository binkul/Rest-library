package com.kodilla.library.service;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dto.BookOrderDto;
import com.kodilla.library.domain.dto.BookReturnDto;
import com.kodilla.library.domain.dto.RentalDto;
import com.kodilla.library.domain.exception.book.BookCopyNotAvailableException;
import com.kodilla.library.domain.exception.book.EntityNotFoundException;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.mapper.RentalMapper;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookRepository;
import com.kodilla.library.repository.ReaderRepository;
import com.kodilla.library.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private RentalMapper rentalMapper;

    public List<RentalDto> getAll() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentalMapper.mapToRentalDtoList(rentals);
    }

    public RentalDto getRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.RENT_ERROR, Long.toString(id))));
        return rentalMapper.mapToRentalDto(rental);
    }

    public RentalDto save(BookOrderDto bookOrderDto) {
        String name = bookOrderDto.getName();
        String lastName = bookOrderDto.getLastName();
        String title = bookOrderDto.getTitle();

        Reader reader = readerRepository.findByNameAndLastName(name, lastName)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.READER_ERROR, name, lastName)));
        List<Book> books = bookRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));
        BookCopy bookCopy = getFirstBookCopy(books, title);

        bookCopy.setStatus(Status.LOAN);
        bookCopyRepository.save(bookCopy);
        Rental rental = new Rental(bookCopy, reader, LocalDate.now());
        rentalRepository.save(rental);

        return rentalMapper.mapToRentalDto(rental);
    }

    public RentalDto update(BookReturnDto bookReturnDto) {
        Rental rental = rentalRepository.findById(bookReturnDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.RENT_ERROR, Long.toString(bookReturnDto.getId()))));
        Status status = Status.getStatus(bookReturnDto.getStatus());
        BookCopy bookCopy = rental.getBookCopy();

        if (status == Status.RETURN) {
            bookCopy.setStatus(Status.READY);
        } else if (status == Status.LOST_PAID) {
            bookCopy.setStatus(Status.LOST);
        } else {
            throw new EntityNotFoundException("Only status RETURN and LOST_PAID are available");
        }
        bookCopyRepository.save(bookCopy);
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);

        return rentalMapper.mapToRentalDto(rental);
    }

    private BookCopy getFirstBookCopy(List<Book> books, String title) {
        return books.stream()
                .map(i -> bookCopyRepository.findByBookAndStatus(i, Status.READY))
                .flatMap(Collection::stream)
                .findFirst()
                .orElseThrow(() -> new BookCopyNotAvailableException(title));
    }
}
