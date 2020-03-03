package com.kodilla.library.domain;

import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookRepository;
import com.kodilla.library.repository.ReaderRepository;
import com.kodilla.library.repository.RentalRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentalDaoTestSuite {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private ReaderRepository readerRepository;

    @Test
    public void testRentalSave() {
        //Given
        String title = "W pustyni i w puszczy";
        String author = "Henryk Sienkiewicz";
        Book book = new Book(title, author, 2000);
        BookCopy bookCopy = new BookCopy(book, Status.READY);
        BookCopy bookCopy1 = new BookCopy(book, Status.LOAN);
        BookCopy bookCopy2 = new BookCopy(book, Status.DESTROYED);
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.parse("2003-12-05"));

        Rental rental = new Rental(bookCopy2, reader, LocalDate.now());

        //When
        bookRepository.save(book);
        long bookId = book.getId();
        bookCopyRepository.save(bookCopy);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        long bookCopyId1 = bookCopy.getId();
        long bookCopyId2 = bookCopy1.getId();
        long bookCopyId3 = bookCopy2.getId();
        readerRepository.save(reader);
        long readerId = reader.getId();
        rentalRepository.save(rental);
        long idRental = rental.getId();
        Rental findRental= rentalRepository.findById(idRental).orElse(null);

        //Then
        Assert.assertNotNull(findRental);
        Assert.assertEquals(Status.DESTROYED, findRental.getBookCopy().getStatus());

        //Clean
        rentalRepository.deleteById(idRental);
        readerRepository.deleteById(readerId);
        bookCopyRepository.deleteById(bookCopyId3);
        bookCopyRepository.deleteById(bookCopyId2);
        bookCopyRepository.deleteById(bookCopyId1);
        bookRepository.deleteById(bookId);
    }
}
