package com.kodilla.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookWithCopyDto {
    private Long id;
    private String title;
    private String author;
    private int published;
    private int copies;
}
