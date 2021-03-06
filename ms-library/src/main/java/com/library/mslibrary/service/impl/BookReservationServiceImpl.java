package com.library.mslibrary.service.impl;

import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.enumerated.BookReservationStatusEnum;
import com.library.mslibrary.proxies.MicroServiceBatchProxy;
import com.library.mslibrary.repository.BookReservationRepository;
import com.library.mslibrary.service.BookReservationService;
import com.library.mslibrary.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BookReservationServiceImpl implements BookReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookReservationServiceImpl.class);

    @Autowired
    BookReservationRepository bookReservationRepository;

    @Autowired
    BookService bookService;

    @Autowired
    private ApplicationPropertiesConfig applicationPropertiesConfig;

    @Override
    public List<BookReservation> findAll() {
        return bookReservationRepository.findAll();
    }

    @Override
    public BookReservation findBookReservationById(Long id) {
        return bookReservationRepository.findBookReservationById(id);
    }

    @Override
    public List<BookReservation> findBookReservationsByUserId(Long userId) {
        List<BookReservation> bookReservationList = bookReservationRepository.findBookReservationsByUserIdAndFilteredByStatusList(userId, getCurrentBookReservationStatusList());
        LOGGER.info("Envoie d'une liste de {} reservations (utilisateur id = {}).", bookReservationList.size(), userId);
        return bookReservationList;
    }

    @Override
    public List<BookReservation> findBookReservationsByBookId(Long bookId) {
        List<BookReservation> bookReservationList = bookReservationRepository.findBookReservationsByBookIdAndFilteredByStatusList(bookId, getCurrentBookReservationStatusList());
        LOGGER.info(
                "Envoie d'une liste de {} réservations (livre id = {})",
                bookReservationList.size(),
                bookId);
        bookReservationList.forEach(e -> LOGGER.info(
                "Utilisateur : {} - Date de réservation : {} (Status {})",
                e.getUser().getEmail(),
                e.getCreationDate(),
                e.getReservationStatus()));
        return bookReservationList;
    }

    @Override
    public BookReservation saveBookReservation(BookReservation bookReservation) {
        BookReservation result = bookReservationRepository.save(bookReservation);
        updateBookReservationAvailable(result.getBook());
        return result;
    }

    private void updateBookReservationAvailable(Book book) {
        if (book != null && !computeIsReservationAvailable(book)){
            book.setReservationAvailable(false);
            bookService.saveBook(book);
        }
    }

    @Override
    public BookReservation closeBookReservation(Long bookReservationId) {
        BookReservation br = bookReservationRepository.findBookReservationById(bookReservationId);

        if (br != null) {
            br.setClosingDate(new Date());
            br.setReservationStatus(BookReservationStatusEnum.CLOSED.toString());
            LOGGER.info("Clôture de la réservation id {} (Status {} - Date de clôture: {})", bookReservationId, br.getReservationStatus(), br.getClosingDate());
            updateBookReservationAvailable(br.getBook());
            return bookReservationRepository.save(br);
        } else {
            return null;
        }
    }

    @Override
    public void changeBookReservationStatus(Long bookReservationId, String bookReservationStatus) {
        BookReservation br = bookReservationRepository.findBookReservationById(bookReservationId);
        if (br != null && !br.getReservationStatus().equals(bookReservationStatus)) {
            br.setReservationStatus(bookReservationStatus);
            if (bookReservationStatus.equals(BookReservationStatusEnum.NOTIFIED.toString())) {
                br.setNotificationDate(new Date());
            }
            if (bookReservationStatus.equals(BookReservationStatusEnum.CLOSED.toString())) {
                br.setClosingDate(new Date());
            }
            if (bookReservationStatus.equals(BookReservationStatusEnum.EXPIRED.toString())) {
                br.setClosingDate(new Date());
            }
            bookReservationRepository.save(br);
        } else if (br == null) {
            LOGGER.info(
                    "Changement de statut '{}' non effectué - Pas de réservation trouvée pour l'Id {}",
                    bookReservationStatus,
                    bookReservationId
            );
        } else if (br.getReservationStatus().equals(bookReservationStatus)) {
            LOGGER.info(
                    "Changement de statut '{}' non effectué - La reservation trouvée est en statut {} pour l'Id {}",
                    bookReservationStatus,
                    br.getReservationStatus(),
                    bookReservationId
            );
        }
    }

    @Override
    public List<BookReservation> saveAll(List<BookReservation> bookReservationList) {
        return bookReservationRepository.saveAll(bookReservationList);
    }

    @Override
    public Integer nbBookReservation(Book book, List<String> bookReservationStatus) {
        if (book != null) {
            return bookReservationRepository.countBookReservationByBookAndFilteredByStatusList(book, bookReservationStatus);
        } else {
            return 0;
        }
    }

    @Override
    public BookReservation findBookReservationByUserIdAndByBookId(Long userId, Long bookId) {
        return bookReservationRepository.findBookReservationsByUserIdAndByBookIdAndFilteredByStatusList(userId, bookId, getCurrentBookReservationStatusList());
    }

    @Override
    public Integer computePositionOfBookReservation(BookReservation bookReservation) {
        Integer result = 0;
        int index = 1;
        List<BookReservation> brList = bookReservationRepository.findBookReservationsByBookIdAndFilteredByStatusList(bookReservation.getBook().getId(), getCurrentBookReservationStatusList());
        for (BookReservation br : brList) {
            if (br.getUser().getId() == bookReservation.getUser().getId()) {
                result = index;
                break;
            }
            index+=1;
        }
        return result;
    }

    @Override
    public boolean computeIsReservationAvailable(Book book){
        return this.nbBookReservation(
                book,
                getCurrentBookReservationStatusList()
        ) < (book.getNbCopy()*applicationPropertiesConfig.getBookReservationFactorLimit());
    }

    @Override
    public List<String> getCurrentBookReservationStatusList() {
        return Arrays.asList(
                BookReservationStatusEnum.IN_PROGRESS.toString(),
                BookReservationStatusEnum.NOTIFIED.toString()
        );
    }

}
