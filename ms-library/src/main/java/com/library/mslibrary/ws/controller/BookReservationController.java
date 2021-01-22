package com.library.mslibrary.ws.controller;

import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.enumerated.BookReservationStatusEnum;
import com.library.mslibrary.service.BookReservationService;
import com.library.mslibrary.service.BookService;
import com.library.mslibrary.ws.exception.NoSuchResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookReservationController.class);

    @Autowired
    BookReservationService bookReservationService;

    @Autowired
    BookService bookService;

    @Autowired
    private ApplicationPropertiesConfig applicationPropertiesConfig;

    @GetMapping(value= ApiRegistration.REST_BOOK_RESERVATIONS_LIST_BY_USER_ID + "/{userId}")
    public List<BookReservation> findBookReservationsListByUserId(@PathVariable String userId) throws NoSuchResultException {
        LOGGER.debug("findBookReservationsListByUserId for userId = {}", userId);
        List<BookReservation> bookReservationList = bookReservationService.findBookReservationsByUserId(Long.parseLong(userId));
        LOGGER.info("Envoi d'une liste de {} réservations", bookReservationList.size());
        //TODO : return pageable with properties
        LOGGER.debug("PageSizeLimit = {}", applicationPropertiesConfig.getPageSizeLimit());
        return bookReservationList;
    }

    @GetMapping(value= ApiRegistration.REST_BOOK_RESERVATIONS_LIST)
    public List<BookReservation> findBookReservationsList() throws NoSuchResultException {
        List<BookReservation> bookReservationList = bookReservationService.findAll();
        LOGGER.info("Envoi d'une liste de {} réservations", bookReservationList.size());
        //TODO : return pageable with properties
        LOGGER.debug("PageSizeLimit = {}", applicationPropertiesConfig.getPageSizeLimit());
        return bookReservationList;
    }

    @GetMapping(value= ApiRegistration.REST_NB_CURRENT_BOOK_RESERVATIONS + "/{bookId}")
    public Integer getNbCurrentBookReservations(@PathVariable Long bookId) {
        Integer result = bookReservationService.nbBookReservation(
                bookService.findBookById(bookId),
                bookReservationService.getCurrentBookReservationStatusList()
        );
        LOGGER.info("Récupération du nombre de réservation pour le Livre id {} : \n==> {} Réservations", bookId, result);
        return result;
    }

    @PostMapping(value= ApiRegistration.REST_NB_CURRENT_BOOKLIST_RESERVATIONS)
    public Map<Integer, Integer> getNbCurrentBookListReservations(@RequestBody List<Book> bookList) {
        Map<Integer, Integer> result = new HashMap<>();
        bookList.stream().forEach(b -> result.put(b.getId().intValue(), getNbCurrentBookReservations(b.getId())));
        LOGGER.debug("{} Livres : ", bookList.size());
        result.entrySet().stream().forEach(k -> LOGGER.debug("id = {} --> nbReservation = {}",k.getKey(), k.getValue()));
        return result;
    }

}
