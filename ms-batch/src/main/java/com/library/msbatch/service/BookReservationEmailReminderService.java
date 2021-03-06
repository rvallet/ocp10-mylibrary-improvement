package com.library.msbatch.service;


import com.library.msbatch.entities.BookReservationEmailReminder;

import java.util.List;

public interface BookReservationEmailReminderService {

    void feedBookReservationEmailReminderRepository(Long bookId);

    void closeBookReservationAfterDeadline();

    void saveBookReservationEmailReminder(BookReservationEmailReminder bookReservationEmailReminder);

    List<BookReservationEmailReminder> findBookReservationEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent);

}
