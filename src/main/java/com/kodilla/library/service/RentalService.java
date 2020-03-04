package com.kodilla.library.service;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dto.BookRentalDto;
import com.kodilla.library.domain.exception.book.BookCopyNotAvailableException;
import com.kodilla.library.domain.exception.book.ErrorMessages;
import com.kodilla.library.domain.exception.book.RecordNotFoundException;
import com.kodilla.library.domain.simple.BookRental;
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
import java.util.stream.Collectors;

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

    public List<BookRentalDto> getRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentalMapper.mapToBookRentalDtoList(
                rentals.stream()
                    .map(this::getNewBookRental)
                    .collect(Collectors.toList())
        );
    }

    public BookRentalDto createRent(BookRentalDto bookRentalDto) {
        BookRental bookRental = rentalMapper.mapToBookRental(bookRentalDto);
        String name = bookRental.getName();
        String lastName = bookRental.getLastName();
        String title = bookRental.getTitle();

        Reader reader = readerRepository.findByNameAndLastName(name, lastName)
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.READER_ERROR, name, lastName)));
        List<Book> books = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.BOOK_ERROR, title)));
        BookCopy bookCopy = getFirstBookCopy(books, title);

        bookCopy.setStatus(Status.LOAN);
        bookCopyRepository.save(bookCopy);
        Rental rental = new Rental(bookCopy, reader, LocalDate.now());
        rentalRepository.save(rental);

        BookRental result = new BookRental(rental.getId(), rental.getRentDate(), rental.getReturnDate(), name, lastName, title, bookCopy.getStatus().toString());
        return rentalMapper.mapToBookRentalDto(result);
    }

    public BookRentalDto returnBook(BookRentalDto bookRentalDto) {
        BookRental bookRental = rentalMapper.mapToBookRental(bookRentalDto);
        Rental rental = rentalRepository.findById(bookRental.getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format(ErrorMessages.RENT_ERROR, Long.toString(bookRental.getId()))));
        Status status = Status.getStatus(rentalMapper.mapToBookRental(bookRentalDto).getStatus());
        BookCopy bookCopy = rental.getBookCopy();

        if (status == Status.RETURN) {
            bookCopy.setStatus(Status.READY);
        } else if (status == Status.LOST_PAID) {
            bookCopy.setStatus(Status.LOST);
        } else {
            throw new RecordNotFoundException("Only status RETURN and LOST_PAID are available");
        }
        bookCopyRepository.save(bookCopy);
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);

        BookRental result = new BookRental(rental.getId(), rental.getRentDate(), rental.getReturnDate(), rental.getName(), rental.getLastName(), rental.getTitle(), bookCopy.getStatus().toString());

        return rentalMapper.mapToBookRentalDto(result);
    }

    private BookCopy getFirstBookCopy(List<Book> books, String title) {
        return books.stream()
                .map(i -> bookCopyRepository.findByBookAndStatus(i, Status.READY))
                .flatMap(Collection::stream)
                .findFirst()
                .orElseThrow(() -> new BookCopyNotAvailableException(title));
    }

    private BookRental getNewBookRental(Rental rental) {
        return new BookRental(rental.getId(), rental.getRentDate(), rental.getReturnDate(), rental.getName(), rental.getLastName(), rental.getTitle(), rental.getStatus().toString());
    }
}
