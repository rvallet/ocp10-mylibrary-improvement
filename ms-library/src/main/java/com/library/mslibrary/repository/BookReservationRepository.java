package com.library.mslibrary.repository;

import com.library.mslibrary.entities.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, String> {

    List<BookReservation> findAll();
    BookReservation findBookReservationById (Long id);

    /*
        SELECT * FROM book_reservation br
        WHERE br.id_user = 1;
     */
    //@Query("SELECT br from BookReservation br WHERE br.user.id = ?1")
    List<BookReservation> findBookReservationsByUserId (Long userId);
}
