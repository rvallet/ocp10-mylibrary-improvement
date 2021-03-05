package com.library.mslibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.mslibrary.enumerated.BookLoanStatusEnum;
import com.library.mslibrary.utils.DateTools;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="book_loan")
public class BookLoan implements Serializable {

    @Id
    @Column(name="id_book_loan")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Date startLoan;

    private Date endLoan;

    private Date returnLoan;

    private Boolean isLoanExtended;

    private String loanStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book")
    @JsonIgnore
    private Book book;

    public BookLoan() {
        super();
        this.startLoan = new Date();
        this.isLoanExtended = false;
        this.loanStatus = BookLoanStatusEnum.IN_PROGRESS.toString();
    }

    public BookLoan(User user, Book book, int bookLoanDuration) {
        this.user = user;
        this.book = book;
        this.startLoan = new Date();
        this.endLoan = DateTools.addDays(new Date(), bookLoanDuration);
        this.isLoanExtended = false;
        this.loanStatus = BookLoanStatusEnum.IN_PROGRESS.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartLoan() {
        return startLoan;
    }

    public void setStartLoan(Date startLoan) {
        this.startLoan = startLoan;
    }

    public Date getEndLoan() {
        return endLoan;
    }

    public void setEndLoan(Date endLoan) {
        this.endLoan = endLoan;
    }

    public Date getReturnLoan() { return returnLoan; }

    public void setReturnLoan(Date returnLoan) { this.returnLoan = returnLoan; }

    public Boolean getLoanExtended() {
        return isLoanExtended;
    }

    public void setLoanExtended(Boolean loanExtended) {
        isLoanExtended = loanExtended;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
