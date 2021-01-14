package com.library.msbatch.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="book_loan_email_reminder")
public class BookLoanEmailReminder implements Serializable {

    @Id
    @Column(name="id_book_loan_email_reminder")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String userEmail;

    private String lastname;

    private String firstname;

    private Long bookId;

    private String bookTitle;

    private Long bookLoanId;

    private Date endLoan;

    private Date sendingEmailDate;

    private Boolean isEmailSent;

    public BookLoanEmailReminder() {
        super();
        this.isEmailSent = false;
    }

    public BookLoanEmailReminder(Long userId, String userEmail, String lastname, String firstname, Long bookId, String bookTitle, Long bookLoanId, Date endLoan) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.lastname = lastname;
        this.firstname = firstname;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookLoanId = bookLoanId;
        this.endLoan = endLoan;
        this.isEmailSent = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookLoanId() {
        return bookLoanId;
    }

    public void setBookLoanId(Long bookLoanId) {
        this.bookLoanId = bookLoanId;
    }

    public Date getSendingEmailDate() {
        return sendingEmailDate;
    }

    public void setSendingEmailDate(Date sendingEmailDate) {
        this.sendingEmailDate = sendingEmailDate;
    }

    public Boolean getEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        isEmailSent = emailSent;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getEndLoan() {
        return endLoan;
    }

    public void setEndLoan(Date endLoan) {
        this.endLoan = endLoan;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
