package com.example.cosinuslibrarysystem.controller;

import com.example.cosinuslibrarysystem.dtos.request.OrderBookCreateDto;
import com.example.cosinuslibrarysystem.dtos.response.OrderBookResponse;
import com.example.cosinuslibrarysystem.service.orderBook.OrderBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderBookController {
    private final OrderBookService orderBookService ;


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<OrderBookResponse> create(@RequestBody OrderBookCreateDto orderBookCreateDto){
        OrderBookResponse orderBookResponse = orderBookService.create(orderBookCreateDto);
        return ResponseEntity.ok(orderBookResponse);
    }



    @GetMapping("/getBook")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<OrderBookResponse> getBook(@RequestParam UUID userId , @RequestParam UUID bookId){
        OrderBookResponse orderBookResponse = orderBookService.getBook(userId , bookId);

        return ResponseEntity.ok(orderBookResponse) ;
    }

    @GetMapping("/returnBook")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> returnBook(@RequestParam UUID userId , @RequestParam UUID bookId){
        String res = orderBookService.returnBook(userId , bookId);

        return ResponseEntity.ok(res) ;
    }


    @GetMapping("/getAllOrderBookIsGetTrue")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<ArrayList<OrderBookResponse>> getAllOrderBookIsGetTrue(){

        ArrayList<OrderBookResponse> orderBookResponses = orderBookService.getAllOrderBookIsGetTrue();

        return ResponseEntity.ok(orderBookResponses) ;
    }



    @GetMapping("/getAllOrderBookIsGetFalse")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<ArrayList<OrderBookResponse>> getAllOrderBookIsGetFalse(){

        ArrayList<OrderBookResponse> orderBookResponses = orderBookService.getAllOrderBookIsGetFalse();

        return ResponseEntity.ok(orderBookResponses) ;
    }




}
