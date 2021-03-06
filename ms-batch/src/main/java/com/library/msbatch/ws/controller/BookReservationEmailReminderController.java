package com.library.msbatch.ws.controller;

import com.library.msbatch.api.ApiRegistration;
import com.library.msbatch.job.BookReservationEmailReminderJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookReservationEmailReminderController {

    @Autowired
    BookReservationEmailReminderJob bookReservationEmailReminderJob;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookReservationEmailReminderController.class);

    @GetMapping(value= ApiRegistration.LAUNCH_BOOK_RESERVATION_EMAIL_REMINDER)
    public void launchBookReservationEmailReminder() {
        LOGGER.info("Reception d'une demande d'envoi d'email de rappel pour les livres reservés");
        bookReservationEmailReminderJob.doJob();
    }

    @GetMapping(value = ApiRegistration.FEED_BOOK_RESERVATION_EMAIL_REMINDER_DB)
    public void feedBookReservationEmailReminderRepository(){
        LOGGER.info("Reception d'une demande d'alimentation feedBookReservationEmailReminder en BDD");
        // TODO
    }

    @GetMapping(value=ApiRegistration.AVAILABLE_BOOK_NOTIFICATION + "/{bookId}")
    public void availableBookNotification(@PathVariable("bookId") Long bookId){
        LOGGER.info("Reception d'une notification de livre disponible id {}", bookId);
        bookReservationEmailReminderJob.feedBookReservationEmailReminderRepository(bookId);
    }

}
