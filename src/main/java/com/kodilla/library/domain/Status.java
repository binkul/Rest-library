package com.kodilla.library.domain;

import com.kodilla.library.domain.exception.book.RecordNotFoundException;

import java.util.stream.Stream;

public enum Status {
    READY,
    LOAN,
    LOST,
    LOST_PAID,
    DESTROYED,
    RETURN;

    public static Status getStatus(String status) {
        return Stream.of(Status.values())
            .filter(i -> i.name().equals(status.toUpperCase()))
            .findFirst()
            .orElseThrow(() -> new RecordNotFoundException("Could not find status name: '" + status + "'. Available are READY, LOAN, LOST, LOST_PAID, DESTROYED, RETURN."));
    }

}
