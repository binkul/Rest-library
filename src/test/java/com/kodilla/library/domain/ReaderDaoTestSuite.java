package com.kodilla.library.domain;

import com.kodilla.library.repository.ReaderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReaderDaoTestSuite {
    @Autowired
    private ReaderRepository readerRepository;

    @Test
    public void testReaderSave() {
        //Given
        Reader reader = new Reader("Jacek", "Binkul", LocalDate.parse("1980-03-20"));
        Reader reader1 = new Reader("Jan", "Kowalski", LocalDate.parse("2003-12-05"));

        //When
        readerRepository.save(reader);
        readerRepository.save(reader1);
        long readerId = reader.getId();
        long readerId1 = reader1.getId();
        int count = readerRepository.findAll().size();

        //Then
        Assert.assertNotEquals(0, count);

        //Clean
        readerRepository.deleteById(readerId);
        readerRepository.deleteById(readerId1);
    }
}
