package com.library.msbatch.service;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendBookLoanReminderEmail();

    void sendBookReservationReminderEmail();

}
