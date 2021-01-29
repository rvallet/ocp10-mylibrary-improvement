package com.library.mslibrary.ws.controller;

import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
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
public class BookReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookReservationController.class);

    @Autowired
    BookReservationService bookReservationService;

    @Autowired
    BookService bookService;

    @Autowired
    private ApplicationPropertiesConfig applicationPropertiesConfig;

    @GetMapping(value= ApiRegistration.REST_BOOK_RESERVATIONS_LIST_BY_USER_ID + "/{userId}")
    public List<BookReservation> findBookReservationsListByUserId(@PathVariable Long userId) throws NoSuchResultException {
        LOGGER.debug("findBookReservationsListByUserId for userId = {}", userId);
        List<BookReservation> bookReservationList = bookReservationService.findBookReservationsByUserId(userId);
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

    @PostMapping(value= ApiRegistration.REST_GET_USER_POSITION_IN_BOOK_RESERVATION)
    public Integer getUserPositionInBookReservation(@RequestBody BookReservation br) {
        Integer result = bookReservationService.computePositionOfBookReservation(br);
        LOGGER.info(
                "Récupération de la position de l'utilisateur id {} pour la réservation pour le Livre id {} : \nPosition ==> {}",
                br.getUser().getId(),
                br.getBook().getId(),
                result);
        return result;
    }

    @GetMapping(value= ApiRegistration.REST_GET_USER_POSITIONS_LIST_IN_BOOK_RESERVATIONS + "/{userId}")
    public Map<Integer, Integer> getUserPositionsListInBookReservation(@PathVariable Long userId) {
        Map<Integer, Integer> result = new HashMap<>();
        List<BookReservation> brList = bookReservationService.findBookReservationsByUserId(userId);
        for (BookReservation br : brList) {
            result.put(Integer.valueOf(String.valueOf(br.getBook().getId())), this.getUserPositionInBookReservation(br));
        }
        LOGGER.info(
                "Récupération des positions de l'utilisateur id {} pour ses réservations",
                userId);
        return result;
    }

    @PostMapping(value= ApiRegistration.REST_CREATE_BOOK_RESERVATION)
    public BookReservation createBookReservation(@RequestBody BookReservation bookReservation) {
        if (bookReservation==null || bookReservation.getBook()==null || bookReservation.getUser()==null) throw new NoSuchResultException("Demande d'enregistrement de réservation : ECHEC");
        BookReservation result = new BookReservation(bookReservation.getUser(), bookReservation.getBook());
        LOGGER.info(
                "Création d'une réservation du livre id {} pour l'utilisateur id {}",
                bookReservation.getBook().getId(),
                bookReservation.getUser().getId()
        );
        return bookReservationService.saveBookReservation(result);
    }

    @GetMapping(value= ApiRegistration.REST_CLOSE_BOOK_RESERVATION + "/{bookReservationId}")
    public BookReservation closeBookReservation(@PathVariable Long bookReservationId) {
        LOGGER.info(
                "Archivage de la Réservation id {}",
                bookReservationId);
        return bookReservationService.closeBookReservation(bookReservationId);
    }

}
