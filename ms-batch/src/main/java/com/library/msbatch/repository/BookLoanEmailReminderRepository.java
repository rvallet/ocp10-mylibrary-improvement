package com.library.msbatch.repository;

import com.library.msbatch.entities.BookLoanEmailReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookLoanEmailReminderRepository extends JpaRepository<BookLoanEmailReminder, Long> {

    List<BookLoanEmailReminder> findAll();
    BookLoanEmailReminder findBookLoanEmailReminderById(Long id);

    /*
    SELECT * FROM library_bdd.book_loan_email_reminder
    WHERE is_email_sent NOT IN (TRUE)*/
    @Query("SELECT bl FROM BookLoanEmailReminder bl WHERE bl.isEmailSent NOT IN (:isEmailSent)")
    List<BookLoanEmailReminder> findBookLoanEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent);

    List<BookLoanEmailReminder> findBookLoanEmailRemindersByBookId(Long bookId);
    List<BookLoanEmailReminder> findBookLoanEmailRemindersByUserId(Long userId);
    List<BookLoanEmailReminder> findBookLoanEmailRemindersByBookLoanId(Long bookLoanId);


}
