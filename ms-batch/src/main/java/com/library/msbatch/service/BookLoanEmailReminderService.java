package com.library.msbatch.service;

import com.library.msbatch.beans.BookLoanBean;
import com.library.msbatch.entities.BookLoanEmailReminder;

import java.util.List;

public interface BookLoanEmailReminderService {

    List<BookLoanBean> getBookLoansList();

    void feedBookLoanEmailReminderRepository();

    void saveBookLoanEmailReminderList(List<BookLoanEmailReminder> bookLoanEmailReminderList);

    List<BookLoanEmailReminder> findBookLoanEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent);

    void saveBookLoanEmailReminder(BookLoanEmailReminder bookLoanEmailReminder);

}
