package com.example.cosinuslibrarysystem.service.orderBook;


import com.example.cosinuslibrarysystem.dtos.request.OrderBookCreateDto;
import com.example.cosinuslibrarysystem.dtos.response.OrderBookResponse;
import com.example.cosinuslibrarysystem.entity.Book;
import com.example.cosinuslibrarysystem.entity.OrderBook;
import com.example.cosinuslibrarysystem.entity.User;
import com.example.cosinuslibrarysystem.exception.*;
import com.example.cosinuslibrarysystem.repository.BookRepository;
import com.example.cosinuslibrarysystem.repository.OrderBookRepository;
import com.example.cosinuslibrarysystem.service.book.BookService;
import com.example.cosinuslibrarysystem.service.user.UserService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderBookServiceImpl implements OrderBookService {
    private final UserService userService ;
    private final BookService bookService ;
    private final BookRepository bookRepository ;
    private final ModelMapper modelMapper ;
    private final OrderBookRepository orderBookRepository ;

    @Override
    public ArrayList<OrderBookResponse> getAllOrderBookIsGetFalse() {
        Optional<ArrayList<OrderBook>> orderBooksByIsGetFalse = orderBookRepository.getAllOrderBookIsGetFalse();
        return check(orderBooksByIsGetFalse);
    }

    @Override
    public ArrayList<OrderBookResponse> getAllOrderBookIsGetTrue() {
        Optional<ArrayList<OrderBook>> orderBooksByIsGetTrue = orderBookRepository.getOrderBooksByIsGetTrue();
        return check(orderBooksByIsGetTrue);


    }
    private ArrayList<OrderBookResponse> check(Optional<ArrayList<OrderBook>> orderBooks){
        if (orderBooks.isEmpty()){
            throw new DataNotFoundException("No rental books found");
        }
        else{
            ArrayList<OrderBookResponse> orderBookResponses = new ArrayList<>() ;
            for (OrderBook orderBook : orderBooks.get()) {
                orderBookResponses.add(OrderBookResponse.builder().bookName(orderBook.getBook().getName()).
                        author(orderBook.getBook().getAuthor()).
                        endTime(orderBook.getValidityPeriod()).
                        pickUpTime(orderBook.getPickUpTime()).
                        returnTime(orderBook.getReturnTime()).
                        build());
            }
            return orderBookResponses ;
        }
    }

    @Override
    public String returnBook(UUID userId, UUID bookId) {
        User userByID = userService.getUserByID(userId);
        Optional<Book> book = bookService.findBookById(bookId);
        checkUserAndBook(userByID , book);
        Optional<OrderBook> orderBookByUserAndBook = orderBookRepository.getOrderBookByUserAndBook(userByID, book.get());


        if (orderBookByUserAndBook.get().getReturnTime().isBefore(LocalDateTime.now())){
            throw new FineException("You submitted late pay penalty");
        }
        else if (orderBookByUserAndBook.get().isGet()){
            orderBookByUserAndBook.get().setDelete(true);
            orderBookRepository.save(orderBookByUserAndBook.get());
            return "Success returned book ";
        }else throw new DataNotFoundException("There is an error");

    }

    void checkUserAndBook(User user , Optional<Book> book){
        if (user==null) {
            throw new DataNotFoundException("User not found") ;
        }
        else  if (book.isEmpty()){
            throw new DataNotFoundException("Book not found");
        }
    }
    @Override
    public OrderBookResponse getBook(UUID userId, UUID bookId) {
        User userByID = userService.getUserByID(userId);
        Optional<Book> book = bookService.findBookById(bookId);
        checkUserAndBook(userByID , book);

        Optional<OrderBook> orderBookByUserAndBook = orderBookRepository.getOrderBookByUserAndBook(userByID, book.get());

        OrderBook orderBook = orderBookByUserAndBook.get();
        if (orderBook.getValidityPeriod().isBefore(LocalDateTime.now())){
            orderBookRepository.delete(orderBook);
            book.get().setRent(false);
            bookRepository.save(book.get());
            throw new ReservationTimeHasExpiredException("Reservation time has expired");
        } else if (orderBook.isGet()) {
            throw new OldOrderBookException("Old rented book");

        } else{
            orderBook.setGet(true);
            orderBookRepository.save(orderBook);
            return OrderBookResponse.builder().bookName(orderBook.getBook().getName()).
                    author(orderBook.getBook().getAuthor()).
                    endTime(orderBook.getValidityPeriod()).
                    pickUpTime(orderBook.getPickUpTime()).
                    returnTime(orderBook.getReturnTime()).
                    build();
        }


    }

    @Override
    public OrderBookResponse create(OrderBookCreateDto orderBookCreateDto) {

        User userByID = userService.getUserByID(orderBookCreateDto.getUserId());
        Optional<Book> book = bookService.findBookById(orderBookCreateDto.getBookId());


        if (userByID==null) {
            throw new DataNotFoundException("User not found") ;
        }
        else  if (book.isEmpty()){
            throw new DataNotFoundException("Book not found");
        }
        else if (book.get().isRent()){
            throw new RentException("Book rental");
        }
        else{

            OrderBook orderBook = OrderBook.builder().
                       user(userByID).
                       book(book.get()).
                       delete(false).
                       validityPeriod(orderBookCreateDto.getPickUpTime().plusDays(3)).
                       pickUpTime(orderBookCreateDto.getPickUpTime()).
                       returnTime(orderBookCreateDto.getReturnTime()).
                       build();
                OrderBook save = orderBookRepository.save(orderBook);
                book.get().setRent(true);
                bookRepository.save(book.get());

                return OrderBookResponse.builder().bookName(save.getBook().getName()).author(save.getBook().getAuthor()).endTime(save.getValidityPeriod()).pickUpTime(save.getPickUpTime()).returnTime(save.getReturnTime()).build();
            }
    }
}
