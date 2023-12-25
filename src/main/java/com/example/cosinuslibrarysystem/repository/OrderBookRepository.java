package com.example.cosinuslibrarysystem.repository;

import com.example.cosinuslibrarysystem.entity.Book;
import com.example.cosinuslibrarysystem.entity.OrderBook;
import com.example.cosinuslibrarysystem.entity.User;
import com.example.cosinuslibrarysystem.service.orderBook.OrderBookService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface OrderBookRepository extends JpaRepository<OrderBook , UUID> {
    Optional<OrderBook> getOrderBookByUserAndBook(User user , Book book);
    //@Query(value = "SELECT o FROM OrderBook o WHERE o.isGet= :true and not o.delete", nativeQuery = false)
    @Query("SELECT o FROM OrderBook o WHERE o.isGet = true")
    Optional<ArrayList<OrderBook>> getOrderBooksByIsGetTrue();
    @Query("SELECT o FROM OrderBook o WHERE o.isGet = false")
    Optional<ArrayList<OrderBook>> getAllOrderBookIsGetFalse();

}
