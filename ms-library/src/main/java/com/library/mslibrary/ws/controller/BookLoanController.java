package com.library.mslibrary.ws.controller;

import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookLoan;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.service.BookLoanService;
import com.library.mslibrary.service.BookReservationService;
import com.library.mslibrary.service.BookService;
import com.library.mslibrary.ws.exception.NoSuchResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookLoanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookLoanController.class);

    @Autowired
    BookLoanService bookLoanService;

    @Autowired
    BookService bookService;

    @Autowired
    BookReservationService bookReservationService;

    @Autowired
    private ApplicationPropertiesConfig applicationPropertiesConfig;

    @GetMapping(value= ApiRegistration.REST_BOOK_LOANS_LIST_BY_USER_ID + "/{userId}")
    public List<BookLoan> findBookLoansListByUserId(@PathVariable String userId) throws NoSuchResultException {
        LOGGER.debug("findBookLoansListByUserId for userId = {}", userId);
        List<BookLoan> bookLoanList = bookLoanService.findBookLoansByUserId(Long.parseLong(userId));
        LOGGER.info("Envoi d'une liste de {} emprunts", bookLoanList.size());
            //TODO : return pageable with properties
            LOGGER.debug("PageSizeLimit = {}", applicationPropertiesConfig.getPageSizeLimit());
            return bookLoanList;
    }

    @GetMapping(value= ApiRegistration.REST_BOOK_LOANS_LIST)
    public List<BookLoan> findBookLoansList() throws NoSuchResultException {
        List<BookLoan> bookLoanList = bookLoanService.findAll();
        LOGGER.info("Envoi d'une liste de {} emprunts", bookLoanList.size());
        //TODO : return pageable with properties
        LOGGER.debug("PageSizeLimit = {}", applicationPropertiesConfig.getPageSizeLimit());
        return bookLoanList;
    }

    @GetMapping(value= ApiRegistration.REST_BOOK_LOANS_EXTEND + "/{bookLoanId}")
    public BookLoan extendBookLoan(@PathVariable Long bookLoanId) throws NoSuchResultException {
        BookLoan bl = bookLoanService.extendBookLoan(bookLoanId);
        LOGGER.info("Prolongation de l'emprunt bookLoanId = {}", bookLoanId);
        return bl;
    }

    @GetMapping(value= ApiRegistration.REST_BOOK_LOANS_CLOSE + "/{bookLoanId}")
    public BookLoan closeBookLoan(@PathVariable Long bookLoanId) throws NoSuchResultException {
        BookLoan bl = bookLoanService.closeBookLoan(bookLoanId);
        LOGGER.info("Clôture de l'emprunt bookLoanId = {}", bookLoanId);
        return bl;
    }

    @GetMapping(value= ApiRegistration.REST_GET_BOOK_LOAN_BY_ID + "/{bookLoanId}")
    public BookLoan findBookLoanById(@PathVariable Long bookLoanId) throws NoSuchResultException {
        BookLoan bl = bookLoanService.findBookLoanById(bookLoanId);
        LOGGER.info("Recherche de l'emprunt bookLoanId = {}", bookLoanId);
        return bl;
    }

    @PostMapping(value= ApiRegistration.REST_SAVE_BOOK_LOAN)
    public void saveBookLoan(@RequestBody BookLoan bookLoan) {
        if (bookLoan==null) throw new NoSuchResultException("Demande d'enregistrement d'emprunt : ECHEC");
        bookLoanService.saveBookLoan(bookLoan);
    }

    @PostMapping(value= ApiRegistration.REST_CREATE_BOOK_LOAN)
    public void createBookLoan(@RequestBody BookLoan bookLoan) {
        if (bookLoan==null || bookLoan.getBook()==null || bookLoan.getUser()==null) throw new NoSuchResultException("Demande d'enregistrement d'emprunt : ECHEC");

        BookLoan bookLoanToCreate = new BookLoan(bookLoan.getUser(), bookLoan.getBook(), applicationPropertiesConfig.getBookLoanDuration());
        Book bookToUpdate = bookLoanToCreate.getBook();

        bookToUpdate.setStock(bookToUpdate.getStock()-1);
        if (bookToUpdate.getStock() <1){
            bookToUpdate.setLoanAvailable(false);
        }

        LOGGER.info("Création d'un emprunt (Ouvrage : {} - Usager : {}", bookLoanToCreate.getBook().getTitle(), bookLoanToCreate.getUser().getEmail());
        bookService.saveBook(bookToUpdate);
        bookLoanService.saveBookLoan(bookLoanToCreate);

        BookReservation br = bookReservationService.findBookReservationByUserIdAndByBookId(bookLoan.getUser().getId(), bookLoan.getBook().getId());
        if (br != null) {
            bookReservationService.closeBookReservation(br.getId());
            LOGGER.info("Archivage d'une réservation suite à un emprunt \nRéservation id {} (Title : {} - userId : {})", br.getId(), br.getBook().getTitle(), br.getUser().getId());
        }
    }

    @GetMapping(value= ApiRegistration.REST_GET_NEXT_BOOKLOAN_ENDDATE + "/{bookloanId}")
    public String getNextBookloanEndDate(@PathVariable Long bookId) {
        String result = bookLoanService.getNextBookloanEndDate(bookId);
        if (!result.isEmpty()) {
            LOGGER.info("Prochaine échéance d'emprunt du Livre id {} : {}", bookId, result);
        }
        return result;
    }

    @PostMapping(value= ApiRegistration.REST_GET_NEXT_BOOKLOAN_ENDDATE_LIST)
    public Map<Integer, String> getNextBookloanEnddateList(@RequestBody List<Book> bookList) {
        Map<Integer, String> result = new HashMap<>();
        bookList.stream().forEach(b -> result.put(b.getId().intValue(), getNextBookloanEndDate(b.getId())));
        LOGGER.debug("{} Livres : ", bookList.size());
        result.entrySet().stream().forEach(k -> LOGGER.info("id = {} --> Échéance = {}",k.getKey(), k.getValue()));
        return result;
    }
}

