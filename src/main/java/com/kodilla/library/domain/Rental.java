package com.kodilla.library.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book_rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookcopy_id", nullable = false)
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    private LocalDate returnDate;

    public Rental(BookCopy bookCopy, Reader reader, LocalDate rentDate) {
        this.bookCopy = bookCopy;
        this.reader = reader;
        this.rentDate = rentDate;
    }

    public String getTitle() {
        return bookCopy.getBook().getTitle();
    }

    public String getName() {
        return reader.getName();
    }

    public String getLastName() {
        return reader.getLastName();
    }

    public Status getStatus() {
        return bookCopy.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return Objects.equals(id, rental.id) &&
                Objects.equals(bookCopy, rental.bookCopy) &&
                Objects.equals(reader, rental.reader) &&
                Objects.equals(rentDate, rental.rentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookCopy, reader, rentDate);
    }
}
