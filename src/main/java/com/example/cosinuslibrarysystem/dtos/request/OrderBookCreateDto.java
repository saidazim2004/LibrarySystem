package com.example.cosinuslibrarysystem.dtos.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderBookCreateDto {
    private UUID bookId ;
    private UUID userId ;
    private LocalDateTime returnTime ;//kitobni qaytarish vaqti - kitobni oqib bolib qaytarish vaqti
    private LocalDateTime pickUpTime ;

}
