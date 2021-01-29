package com.library.mslibrary.service;

import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;

import java.util.List;

public interface BookReservationService {

    List<BookReservation> findAll();
    BookReservation findBookReservationById (Long id);
    List<BookReservation> findBookReservationsByUserId (Long userId);
    BookReservation saveBookReservation (BookReservation bookReservation);
    BookReservation closeBookReservation (Long bookReservationId);
    List<BookReservation> saveAll (List<BookReservation> bookReservationList);
    Integer nbBookReservation(Book book, List<String> bookReservationStatus);
    BookReservation findBookReservationByUserIdAndByBookId(Long userId, Long bookId);
    Integer computePositionOfBookReservation(BookReservation bookReservation);
    boolean computeIsReservationAvailable(Book book);
    List<String> getCurrentBookReservationStatusList();

}