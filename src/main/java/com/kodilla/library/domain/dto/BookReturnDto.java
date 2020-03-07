package com.kodilla.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookReturnDto {
    private Long id;
    private String status;
}
