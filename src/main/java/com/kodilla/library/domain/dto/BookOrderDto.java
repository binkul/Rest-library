package com.kodilla.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookOrderDto {
    private String name;
    private String lastName;
    private String title;
}
