package com.library.msbatch.repository;

import com.library.msbatch.entities.BookReservationEmailReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReservationEmailReminderRepository extends JpaRepository<BookReservationEmailReminder, Long> {

    List<BookReservationEmailReminder> findAll();

    @Query("SELECT br FROM BookReservationEmailReminder br WHERE br.isEmailSent NOT IN (:isEmailSent)")
    List<BookReservationEmailReminder> findBookReservationEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent);

    List<BookReservationEmailReminder> findBookReservationEmailRemindersByIsEmailSent();

    List<BookReservationEmailReminder> findBookReservationEmailRemindersByBookIdAndAndUserIdOrderByBookReservationIdDesc(Long bookId, Long userId);

}
