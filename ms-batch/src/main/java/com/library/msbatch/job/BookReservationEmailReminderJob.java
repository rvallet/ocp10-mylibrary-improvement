package com.library.msbatch.job;

import com.library.msbatch.service.BookReservationEmailReminderService;
import com.library.msbatch.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class BookReservationEmailReminderJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookReservationEmailReminderJob.class);

    @Autowired
    EmailService emailService;

    @Autowired
    BookReservationEmailReminderService bookReservationEmailReminderService;

    // Manual launch with wget "http://localhost:9095/launchBookReservationEmailReminder"
    //@Scheduled(cron="0/30 * * * * ?")
    @Scheduled(cron="0 0 8 * * ?")
    public void doJob(){
        long t1 = System.currentTimeMillis();
        LOGGER.info("Start Job");

        emailService.sendBookReservationReminderEmail();

        long t2 = System.currentTimeMillis();
        LOGGER.info("End Job ({} ms)", t2-t1);
    }

    // Manual launch with wget "http://localhost:9095/closeBookReservation"
    //@Scheduled(cron="0 0/1 * * * ?")
    @Scheduled(cron="0 0 3 * * ?")
    public void closeBookReservation(){
        long t1 = System.currentTimeMillis();
        LOGGER.info("Start Job");

        bookReservationEmailReminderService.closeBookReservationAfterDeadline();

        long t2 = System.currentTimeMillis();
        LOGGER.info("End Job ({} ms)", t2-t1);
    }

    public void feedBookReservationEmailReminderRepository(Long bookId) {
        bookReservationEmailReminderService.feedBookReservationEmailReminderRepository(bookId);
    }

}
