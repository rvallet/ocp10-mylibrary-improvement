package com.library.msbatch.ws.controller;

import com.library.msbatch.api.ApiRegistration;
import com.library.msbatch.job.BookLoanEmailReminderJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookLoanEmailReminderController {

    @Autowired
    BookLoanEmailReminderJob bookLoanEmailReminderJob;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookLoanEmailReminderController.class);

    @GetMapping(value= ApiRegistration.LAUNCH_BOOK_LOAN_EMAIL_REMINDER)
    public void launchBookLoanEmailReminder() {
        LOGGER.info("Reception d'une demande d'envoi d'email de rappel pour les livres empruntés");
        bookLoanEmailReminderJob.doJob();
    }

    @GetMapping(value = ApiRegistration.FEED_BOOK_LOAN_EMAIL_REMINDER_DB)
    public void feedBookLoanEmailReminderRepository(){
        LOGGER.info("Reception d'une demande d'alimentation feedBookLoanEmailReminder en BDD");
        bookLoanEmailReminderJob.feedBookLoanEmailReminderRepository();
    }
}
