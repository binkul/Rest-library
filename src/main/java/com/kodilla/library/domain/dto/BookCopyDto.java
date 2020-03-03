package com.kodilla.library.domain.dto;

import com.kodilla.library.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookCopyDto {
    private Long id;
    private Long bookId;
    private Status status;
}
