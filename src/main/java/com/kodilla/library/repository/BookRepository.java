package com.kodilla.library.repository;

import com.kodilla.library.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Override
    List<Book> findAll();

    @Override
    Optional<Book> findById(Long id);

    Optional<List<Book>> findByTitle(String title);

    @Override
    Book save(Book book);

    @Override
    void deleteById(Long id);

    Optional<Book> findByTitleAndPublished(String title, int published);

    boolean existsBookByTitleAndAuthorAndPublished(String title, String author, int published);
}
