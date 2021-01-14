package com.library.msbatch.service.impl;

import com.library.msbatch.config.ApplicationPropertiesConfig;
import com.library.msbatch.config.EmailConfig;
import com.library.msbatch.config.MailProperties;
import com.library.msbatch.entities.BookLoanEmailReminder;
import com.library.msbatch.service.BookLoanEmailReminderService;
import com.library.msbatch.service.EmailService;
import com.library.msbatch.utils.DateTools;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    ApplicationPropertiesConfig applicationPropertiesConfig;

    @Autowired
    private BookLoanEmailReminderService bookLoanEmailReminderService;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setReplyTo("noreply@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        LOGGER.info("{} --> Envoi d'un email à {} :\n{}",message.getFrom(), to , text);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    @Override
    public void sendBookLoanReminderEmail() {
        List<BookLoanEmailReminder> bookLoanEmailReminderList = bookLoanEmailReminderService.findBookLoanEmailRemindersByIsEmailSentIsNot(true);
        LOGGER.debug("bookLoanEmailReminderList = {} (filter = {})", bookLoanEmailReminderList.size(), "true");
        if (!bookLoanEmailReminderList.isEmpty()) {
            for (BookLoanEmailReminder bookLoanEmailReminder : bookLoanEmailReminderList) {
                String text = String.format(
                        emailConfig.template().getText(),
                        bookLoanEmailReminder.getLastname(),
                        bookLoanEmailReminder.getFirstname(),
                        bookLoanEmailReminder.getBookTitle(),
                        DateTools.dateToStringPatternForEmail(bookLoanEmailReminder.getEndLoan()));

                sendSimpleMessage(bookLoanEmailReminder.getUserEmail(), applicationPropertiesConfig.getObject()+" '"+ bookLoanEmailReminder.getBookTitle()+"'", text);

                bookLoanEmailReminder.setEmailSent(true);
                bookLoanEmailReminder.setSendingEmailDate(new Date());
                bookLoanEmailReminderService.saveBookLoanEmailReminder(bookLoanEmailReminder);
            }
        }
    }

}

