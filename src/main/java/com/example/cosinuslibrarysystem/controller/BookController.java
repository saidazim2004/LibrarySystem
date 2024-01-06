package com.example.cosinuslibrarysystem.controller;


import com.example.cosinuslibrarysystem.dtos.request.BookCreateDto;
import com.example.cosinuslibrarysystem.dtos.response.BookResponse;
import com.example.cosinuslibrarysystem.entity.Polka;
import com.example.cosinuslibrarysystem.service.book.BookService;
import com.example.cosinuslibrarysystem.service.polka.QavatService;
import com.example.cosinuslibrarysystem.service.polka.QavatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService ;
    private final QavatService qavatService ;

    @GetMapping("/addNewBook")
    @PreAuthorize("hasAnyRole('MODERATOR')")
    public ResponseEntity<BookResponse> addNewBook(@RequestBody BookCreateDto bookCreate){

        System.out.println("bookCreate.getBookName() = " + bookCreate.getBookName());
        BookResponse bookResponse  = bookService.create(bookCreate);

        return ResponseEntity.ok(bookResponse) ;
    }

    @GetMapping("/findByBookNameAndAuthor")
    @PreAuthorize("hasAnyRole('MODERATOR')")
    public ResponseEntity<BookResponse> findByBookNameAndAuthor(@RequestParam String bookName , @RequestParam String author){
        BookResponse bookResponse  = bookService.findByBookNameAndAuthor(bookName , author);
        return ResponseEntity.ok(bookResponse) ;
    }


    @GetMapping("/findEmptySpace")
    @PreAuthorize("hasAnyRole('MODERATOR')")
    public ResponseEntity<ArrayList<Polka>> findEmptySpace(UUID qavat){
        System.out.println("qavat = " + qavat);


        ArrayList<Polka> polkas = qavatService.findEmptySpace(qavat);
        return ResponseEntity.ok(polkas);
    }

}
