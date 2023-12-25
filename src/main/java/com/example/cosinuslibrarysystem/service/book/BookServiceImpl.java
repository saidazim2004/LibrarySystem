package com.example.cosinuslibrarysystem.service.book;

import com.example.cosinuslibrarysystem.dtos.request.BookCreateDto;
import com.example.cosinuslibrarysystem.dtos.response.BookResponse;
import com.example.cosinuslibrarysystem.entity.Book;
import com.example.cosinuslibrarysystem.entity.Polka;
import com.example.cosinuslibrarysystem.exception.DataAlreadyExitException;
import com.example.cosinuslibrarysystem.exception.DataNotFoundException;
import com.example.cosinuslibrarysystem.exception.PolkaIsGoneException;
import com.example.cosinuslibrarysystem.repository.BookRepository;
import com.example.cosinuslibrarysystem.repository.PolkaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository ;

    private final PolkaRepository polkaRepository ;

    @Override
    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    public BookResponse findByBookNameAndAuthor(String bookName, String author) {
        Optional<Book> bookByNameAndAuthor = bookRepository.findBookByNameAndAuthor(bookName, author);
        if (bookByNameAndAuthor.isPresent()){
            return BookResponse.builder().bookName(bookByNameAndAuthor.get().getName())
                    .author(bookByNameAndAuthor.get().getAuthor())
                    .page(bookByNameAndAuthor.get().getPage()).isRent(bookByNameAndAuthor.get().isRent()).build();
        }
        throw new DataNotFoundException("Book Not found");
    }

    @Override
    public Optional<Book> findBookById(UUID bookId) {
        return bookRepository.findBookById(bookId);
    }

    @Override
    public BookResponse create(BookCreateDto bookCreate) {
        Optional<Book> bookByAuthorAnAndName = bookRepository.getBookByAuthorAnAndName(bookCreate.getAuthor(), bookCreate.getBookName());
        if (bookByAuthorAnAndName.isPresent()){
            throw new DataAlreadyExitException("Book already had");
        }
        else {

            Optional<Polka> polka = polkaRepository.findById(bookCreate.getPolkaId());
            if (polka.isPresent()){
                long l = bookRepository.countBooksByPolka(polka.get());
                if (l<10){
                    Book book = Book.builder().author(bookCreate.getAuthor())
                            .name(bookCreate.getBookName())
                            .page(bookCreate.getPage())
                            .isRent(false)
                            .polka(polka.get()).build();
                    Book save = bookRepository.save(book);
                    return BookResponse.builder().bookName(save.getName()).author(save.getAuthor()).page(save.getPage())
                            .build();
                }else{
                    throw new PolkaIsGoneException("Polka is gone");
                }
            }else {
                throw new DataNotFoundException("Polka Not Found");
            }

        }

    }
}
