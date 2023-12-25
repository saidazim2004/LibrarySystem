package com.example.cosinuslibrarysystem.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookCreateDto {
    private String bookName ;
    private String author ;
    private double page ;
    private UUID polkaId ;


}
