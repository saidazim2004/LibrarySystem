package com.example.cosinuslibrarysystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class OrderBook extends BaseEntity{
    @ManyToOne
    private User user ;
    @ManyToOne
    private Book book ;
    private LocalDateTime pickUpTime ;//olib ketish vaqti - kutubxonaga mijozni borish vaqti
    private LocalDateTime validityPeriod ;//bron amal qilish muddati - olib ketish vaqtini kiritib bolgandan kegin 3kun muhlat beriladi
    private LocalDateTime returnTime ;//kitobni qaytarish vaqti - kitobni oqib bolib qaytarish vaqti
    private boolean isGet = false ;//bron qilgandan kegin kitobni oldimi yoqmi
    private boolean delete ;


}
