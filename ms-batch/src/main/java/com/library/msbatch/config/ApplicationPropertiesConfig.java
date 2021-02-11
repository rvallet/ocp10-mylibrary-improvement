package com.library.msbatch.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
@ConfigurationProperties("config-ms-batch")
public class ApplicationPropertiesConfig {

    private String bookLoanTemplate;

    private String bookLoanObject;

    private String bookReservationTemplate;

    private String bookReservationObject;

    private int bookReservationDeadline;

    private int bookReservationExpiration;

    public String getBookLoanTemplate() {
        return StringUtils.toEncodedString(bookLoanTemplate.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }

    public void setBookLoanTemplate(String bookLoanTemplate) {
        this.bookLoanTemplate = bookLoanTemplate;
    }

    public String getBookLoanObject() {
        return StringUtils.toEncodedString(bookLoanObject.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }

    public void setBookLoanObject(String bookLoanObject) {
        this.bookLoanObject = bookLoanObject;
    }

    public String getBookReservationTemplate() {
        return StringUtils.toEncodedString(bookReservationTemplate.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }

    public void setBookReservationTemplate(String bookReservationTemplate) {
        this.bookReservationTemplate = bookReservationTemplate;
    }

    public String getBookReservationObject() {
        return StringUtils.toEncodedString(bookReservationObject.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }

    public void setBookReservationObject(String bookReservationObject) {
        this.bookReservationObject = bookReservationObject;
    }

    public int getBookReservationDeadline() {
        return bookReservationDeadline;
    }

    public void setBookReservationDeadline(int bookReservationDeadline) {
        this.bookReservationDeadline = bookReservationDeadline;
    }

    public int getBookReservationExpiration() {
        return bookReservationExpiration;
    }

    public void setBookReservationExpiration(int bookReservationExpiration) {
        this.bookReservationExpiration = bookReservationExpiration;
    }

}
