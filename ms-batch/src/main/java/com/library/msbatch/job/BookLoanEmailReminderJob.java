package com.library.msbatch.job;

import com.library.msbatch.service.BookLoanEmailReminderService;
import com.library.msbatch.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class BookLoanEmailReminderJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookLoanEmailReminderJob.class);

    @Autowired
    EmailService emailService;

    @Autowired
    BookLoanEmailReminderService bookLoanEmailReminderService;

    // Manual launch with wget "http://localhost:9095/launchBookLoanEmailReminder"
    //@Scheduled(cron="0/30 * * * * ?")
    @Scheduled(cron="0 0 8 * * ?")
    public void doJob(){
    long t1 = System.currentTimeMillis();
    LOGGER.info("Start Job");

    emailService.sendBookLoanReminderEmail();

    long t2 = System.currentTimeMillis();
    LOGGER.info("End Job ({} ms)", t2-t1);
    }

    // Manual launch with wget "http://localhost:9095/feedBookLoanEmailReminderRepository"
    //@Scheduled(cron="0 0/1 * * * ?")
    @Scheduled(cron="0 0 3 * * ?")
    public void feedBookLoanEmailReminderRepository(){
        long t1 = System.currentTimeMillis();
        LOGGER.info("Start Job");

        bookLoanEmailReminderService.feedBookLoanEmailReminderRepository();

        long t2 = System.currentTimeMillis();
        LOGGER.info("End Job ({} ms)", t2-t1);
    }
}
