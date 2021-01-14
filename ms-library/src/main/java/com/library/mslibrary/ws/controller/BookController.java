package com.library.mslibrary.ws.controller;

import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.service.BookService;
import com.library.mslibrary.ws.exception.NoSuchResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @Autowired
    private ApplicationPropertiesConfig applicationPropertiesConfig;

    @GetMapping(value= ApiRegistration.REST_BOOKS_LIST)
    public List<Book> getBookList() throws NoSuchResultException {
        LOGGER.debug("getBookList from BookService");
        List<Book> bookList = bookService.findAll();
        LOGGER.info("Envoi d'une liste de {} livres", bookList.size());
        if (bookList.isEmpty()) throw new NoSuchResultException("Aucun Livre");
        //TODO : return pageable with properties
        return bookList;
    }

    @GetMapping(value= ApiRegistration.REST_GET_SEARCH_CRITERIA_LIST)
    public List<String> getSearchCriteriaList(){
        return bookService.getSearchCriteriaList();
    }

    @GetMapping(value= ApiRegistration.REST_GET_BOOK_BY_ID + "/{bookId}")
    public Book getBookById(@PathVariable Long bookId) throws NoSuchResultException {
        LOGGER.debug("getting Book from BookService");
        Book book = bookService.findBookById(bookId);
        LOGGER.info("Envoi du livre id {}", bookId);
        if (book == null) throw new NoSuchResultException("Aucun Livre");
        return book;
    }

    @GetMapping(value= ApiRegistration.REST_BOOK_BY_ISBN + "/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) throws NoSuchResultException {
        LOGGER.debug("getting Book from BookService");
        Optional<Book> book = Optional.ofNullable(bookService.findBookByIsbn(isbn));

        if (book.isPresent()) {
            LOGGER.info("Envoi du livre id {} (isbn : {})", book.get().getId(), isbn);
            return book.get();
        } else {
            return null;
        }
    }

}
