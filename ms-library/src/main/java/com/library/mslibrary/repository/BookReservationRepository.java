package com.library.mslibrary.repository;

import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, String> {

    BookReservation findBookReservationById (Long id);

    @Query("SELECT br FROM BookReservation br WHERE br.reservationStatus IN ?1 ORDER BY br.creationDate ASC")
    List<BookReservation> findAllFilteredByStatusList(List<String> bookReservationStatus);

    @Query("SELECT br FROM BookReservation br WHERE br.user.id = ?1 AND br.reservationStatus IN ?2 ORDER BY br.creationDate ASC")
    List<BookReservation> findBookReservationsByUserIdAndFilteredByStatusList(Long userId, List<String> bookReservationStatus);

    @Query("SELECT br FROM BookReservation br WHERE br.book.id = ?1 AND br.reservationStatus IN ?2 ORDER BY br.creationDate ASC")
    List<BookReservation> findBookReservationsByBookIdAndFilteredByStatusList(Long bookId, List<String> bookReservationStatus);

    @Query("SELECT COUNT(br) FROM BookReservation br WHERE br.book = ?1 AND br.reservationStatus IN ?2 ORDER BY br.creationDate ASC")
    Integer countBookReservationByBookAndFilteredByStatusList(Book book, List<String> bookReservationStatus);

    @Query("SELECT br FROM BookReservation br WHERE br.user.id = ?1 AND br.book.id = ?2 AND br.reservationStatus IN ?3 ORDER BY br.creationDate ASC")
    BookReservation findBookReservationsByUserIdAndByBookIdAndFilteredByStatusList(Long userId, Long bookId, List<String> bookReservationStatus);

}
