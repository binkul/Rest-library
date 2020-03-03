package com.kodilla.library.domain.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookStatus {
    private Long id;
    private String status;
}
