package com.library.mslibrary.ws.controller;

import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.service.BookReservationService;
import com.library.mslibrary.service.BookService;
import com.library.mslibrary.ws.exception.NoSuchResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        LOGGER.info("Envoi d'une liste de {} emprunts", bookReservationList.size());
        //TODO : return pageable with properties
        LOGGER.debug("PageSizeLimit = {}", applicationPropertiesConfig.getPageSizeLimit());
        return bookReservationList;
    }

    @GetMapping(value= ApiRegistration.REST_BOOK_RESERVATIONS_LIST)
    public List<BookReservation> findBookReservationsList() throws NoSuchResultException {
        List<BookReservation> bookReservationList = bookReservationService.findAll();
        LOGGER.info("Envoi d'une liste de {} reservations", bookReservationList.size());
        //TODO : return pageable with properties
        LOGGER.debug("PageSizeLimit = {}", applicationPropertiesConfig.getPageSizeLimit());
        return bookReservationList;
    }


}
