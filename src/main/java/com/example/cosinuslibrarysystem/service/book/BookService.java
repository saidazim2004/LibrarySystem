package com.example.cosinuslibrarysystem.service.book;


import com.example.cosinuslibrarysystem.dtos.request.BookCreateDto;
import com.example.cosinuslibrarysystem.dtos.response.BookResponse;
import com.example.cosinuslibrarysystem.entity.Book;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BookService {
    BookResponse create(BookCreateDto bookCreate);



    Optional<Book> findById(UUID bookId);


    Optional<Book> findBookById(UUID bookId);

    BookResponse findByBookNameAndAuthor(String bookName, String author);
}
