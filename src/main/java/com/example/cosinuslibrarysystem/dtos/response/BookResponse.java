package com.example.cosinuslibrarysystem.dtos.response;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class BookResponse {
    private String bookName ;
    private String author ;
    private boolean isRent ;
    private double page ;

}
