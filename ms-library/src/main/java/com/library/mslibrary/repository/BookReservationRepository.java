package com.library.mslibrary.repository;

import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, String> {

    List<BookReservation> findAll();
    BookReservation findBookReservationById (Long id);

    @Query("SELECT br FROM BookReservation br WHERE br.reservationStatus IN ?1 ORDER BY br.creationDate ASC")
    List<BookReservation> findAllWithActiveStatus(List<String> bookReservationStatus);

    /*
        SELECT * FROM book_reservation br
        WHERE br.id_user = 1;
     */
    @Query("SELECT br FROM BookReservation br WHERE br.user.id = ?1 AND br.reservationStatus IN ?2 ORDER BY br.creationDate ASC")
    List<BookReservation> findBookReservationsByUserId (Long userId, List<String> bookReservationStatus);

    @Query("SELECT br FROM BookReservation br WHERE br.book.id = ?1 AND br.reservationStatus IN ?2 ORDER BY br.creationDate ASC")
    List<BookReservation> findBookReservationsByBookId (Long bookId, List<String> bookReservationStatus);

    @Query("SELECT COUNT(br) FROM BookReservation br WHERE br.book = ?1 AND br.reservationStatus IN ?2 ORDER BY br.creationDate ASC")
    Integer countBookReservationByBookAndReservationStatus(Book book, List<String> bookReservationStatus);

    @Query("SELECT br FROM BookReservation br WHERE br.user.id = ?1 AND br.book.id = ?2 AND br.reservationStatus IN ?3 ORDER BY br.creationDate ASC")
    BookReservation findBookReservationsByUserIdAndByBookId(Long userId, Long bookId, List<String> bookReservationStatus);
}
