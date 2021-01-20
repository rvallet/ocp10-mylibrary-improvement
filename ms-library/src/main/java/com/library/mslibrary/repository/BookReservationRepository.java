package com.library.mslibrary.repository;

import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, String> {

    List<BookReservation> findAll();
    BookReservation findBookReservationById (Long id);

    /*
        SELECT * FROM book_reservation br
        WHERE br.id_user = 1;
     */
    //@Query("SELECT br FROM BookReservation br WHERE br.user.id = ?1")
    List<BookReservation> findBookReservationsByUserId (Long userId);

    @Query("SELECT COUNT(br) FROM BookReservation br WHERE br.book = ?1 AND br.reservationStatus IN ?2")
    Integer countBookReservationByBookAndReservationStatus(Book book, List<String> bookReservationStatus);
}
