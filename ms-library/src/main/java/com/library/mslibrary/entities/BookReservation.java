package com.library.mslibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.mslibrary.enumerated.BookReservationStatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="book_reservation")
public class BookReservation implements Serializable {

    @Id
    @Column(name="id_book_reservation")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Date creationDate;

    private Date closingDate;

    private Date notificationDate;

    private String reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Book book;

    public BookReservation() {
        super();
        this.creationDate = new Date();
        this.reservationStatus = BookReservationStatusEnum.IN_PROGRESS.toString();
    }

    public BookReservation(User user, Book book) {
        this.user = user;
        this.book = book;
        this.creationDate = new Date();
        this.reservationStatus = BookReservationStatusEnum.IN_PROGRESS.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
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

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }
}
