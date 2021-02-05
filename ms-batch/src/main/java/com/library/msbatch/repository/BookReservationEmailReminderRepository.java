package com.library.msbatch.repository;

import com.library.msbatch.entities.BookLoanEmailReminder;
import com.library.msbatch.entities.BookReservationEmailReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReservationEmailReminderRepository extends JpaRepository<BookReservationEmailReminder, Long> {

    List<BookReservationEmailReminder> findAll();

    /*
     SELECT * FROM library_bdd.book_loan_email_reminder
     WHERE is_email_sent NOT IN (TRUE)
        */
    @Query("SELECT br FROM BookReservationEmailReminder br WHERE br.isEmailSent NOT IN (:isEmailSent)")
    List<BookLoanEmailReminder> findBookReservationEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent);

}
