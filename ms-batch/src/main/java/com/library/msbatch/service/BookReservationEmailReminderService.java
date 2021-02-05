package com.library.msbatch.service;


import com.library.msbatch.beans.BookReservationBean;
import com.library.msbatch.entities.BookReservationEmailReminder;

import java.util.List;

public interface BookReservationEmailReminderService {

    List<BookReservationBean> getBookReservationsList();

    void feedBookReservationEmailReminderRepository();

    void saveBookReservationEmailReminderList(List<BookReservationEmailReminder> bookReservationEmailReminderList);

    List<BookReservationEmailReminder> findBookReservationEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent);

    void saveBookReservationEmailReminder(BookReservationEmailReminder bookReservationEmailReminder);

}
