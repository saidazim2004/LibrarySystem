package com.example.cosinuslibrarysystem.repository;

import com.example.cosinuslibrarysystem.entity.Book;
import com.example.cosinuslibrarysystem.entity.Polka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book , UUID> {
    @Query(value = "SELECT b FROM Book b WHERE b.id = :id", nativeQuery = false)
    Optional<Book> findBookById(@Param("id") UUID id);
    @Query(value = "SELECT b FROM Book b WHERE b.author = : author and b.name = : name", nativeQuery = false)
    Optional<Book> getBookByAuthorAnAndName(String author , String name);


    @Query("SELECT COUNT(b) FROM Book b WHERE b.polka = :polka")
    long countBooksByPolka(@Param("polka") Polka polka);

    Optional<Book> findBookByNameAndAuthor(String name , String author);
}


