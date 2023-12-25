package com.example.cosinuslibrarysystem.service.orderBook;

import com.example.cosinuslibrarysystem.dtos.request.OrderBookCreateDto;
import com.example.cosinuslibrarysystem.dtos.response.OrderBookResponse;

import java.util.ArrayList;
import java.util.UUID;

public interface OrderBookService {
    OrderBookResponse create(OrderBookCreateDto orderBookCreateDto);

    OrderBookResponse getBook(UUID userId, UUID bookId);

    String returnBook(UUID userId, UUID bookId);

    ArrayList<OrderBookResponse> getAllOrderBookIsGetTrue();


    ArrayList<OrderBookResponse> getAllOrderBookIsGetFalse();



}
