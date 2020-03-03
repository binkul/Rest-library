package com.kodilla.library.domain.exception.book;

public class BookCopyNotAvailableException extends RuntimeException {
    public BookCopyNotAvailableException(String desc) {
        super("There are currently no books in library: '" + desc + "'");
    }
}
