package com.kodilla.library.domain;

import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTestSuite {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Test
    public void testBookSave() {

        //Given
        String title = "Lalka";
        Book book = new Book("W pustyni i w puszczy", "Henryk Sienkieiwcz", 2000);
        Book book1 = new Book(title, "Bolesław Prus", 1978);
        Book book2 = new Book(title, "Bolesław Prus", 1895);

        //When
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
        long bookId = book.getId();
        long bookId1 = book1.getId();
        long bookId2 = book2.getId();
        List<Book> books = bookRepository.findAll();
        Book returnBook = bookRepository.findByTitleAndPublished(title, 1895).orElse(null);

        //Then
        Assert.assertEquals(3, books.size());
        Assert.assertNotNull(returnBook);

        //Clean
        bookRepository.deleteById(bookId);
        bookRepository.deleteById(bookId1);
        bookRepository.deleteById(bookId2);
    }

    @Test
    public void testBookAndBookCopySave() {
        //Given
        String title = "W pustyni i w puszczy";
        String author = "Henryk Sienkiewicz";
        Book book = new Book(title, author, 2000);
        Book book1 = new Book("Lalka", "Bolesław Prus", 1978);
        BookCopy bookCopy = new BookCopy(book, Status.READY);
        BookCopy bookCopy1 = new BookCopy(book, Status.READY);
        BookCopy bookCopy2 = new BookCopy(book, Status.READY);
        BookCopy bookCopy3 = new BookCopy(book1, Status.LOAN);
        BookCopy bookCopy4 = new BookCopy(book1, Status.LOST);

        //When
        bookRepository.save(book);
        bookCopyRepository.save(bookCopy);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        long bookId = book.getId();
        long bookCopyId = bookCopy.getId();
        long bookCopyId1 = bookCopy1.getId();
        long bookCopyId2 = bookCopy2.getId();

        bookRepository.save(book1);
        bookCopyRepository.save(bookCopy3);
        bookCopyRepository.save(bookCopy4);
        long bookId1 = book1.getId();
        long bookCopyId3 = bookCopy3.getId();
        long bookCopyId4 = bookCopy4.getId();

        List<Book> books = bookRepository.findAll();
        List<BookCopy> copies = bookCopyRepository.findByBook(book);
        List<Book> findedBook = bookRepository.findByTitle(title).orElse(null);
        String bookAuthor = findedBook.get(0).getAuthor();

        //Then
        Assert.assertEquals(2, books.size());
        Assert.assertEquals(3, copies.size());
        Assert.assertEquals(author, bookAuthor);

        //Clean
        bookCopyRepository.deleteById(bookCopyId);
        bookCopyRepository.deleteById(bookCopyId1);
        bookCopyRepository.deleteById(bookCopyId2);
        bookCopyRepository.deleteById(bookCopyId3);
        bookCopyRepository.deleteById(bookCopyId4);

        bookRepository.deleteById(bookId);
        bookRepository.deleteById(bookId1);
    }
}
