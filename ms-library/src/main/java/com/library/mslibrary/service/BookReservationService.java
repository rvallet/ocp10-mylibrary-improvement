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
    boolean computeIsReservationAvailable(Book book);
}
