package com.library.msbatch.api;

public interface ApiRegistration {

    String LAUNCH_BOOK_LOAN_EMAIL_REMINDER = "/launchBookLoanEmailReminder"; //TODO: secure with "admin/launchBookLoanEmailReminder"

    String FEED_BOOK_LOAN_EMAIL_REMINDER_DB = "/feedBookLoanEmailReminderRepository";

    String LAUNCH_BOOK_RESERVATION_EMAIL_REMINDER = "/launchBookReservationEmailReminder";

    String FEED_BOOK_RESERVATION_EMAIL_REMINDER_DB = "/feedBookReservationEmailReminderRepository";
    String AVAILABLE_BOOK_NOTIFICATION = "/availableNotification";
}
