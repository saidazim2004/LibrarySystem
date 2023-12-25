package com.example.cosinuslibrarysystem.dtos.response;

import com.example.cosinuslibrarysystem.entity.Book;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class OrderBookResponse {

    private String bookName ;
    private String author ;
    private LocalDateTime pickUpTime ;//kutubxonadan olib ketish vaqti
    private LocalDateTime returnTime ;//kitobni qaytarish vaqti - kitobni oqib bolib qaytarish vaqti
    private LocalDateTime endTime ;   //
}
