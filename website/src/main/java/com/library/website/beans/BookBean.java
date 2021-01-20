package com.library.website.beans;

import java.util.Collection;
import java.util.Date;

public class BookBean {

    private Long id;

    private String title;

    private String description;

    private String shortDescription;

    private String author;

    private String editor;

    private String collection;

    private String isbn;

    private String imgPathThAttribute;

    private Date releaseDate;

    private int nbCopy;

    private int stock;

    private Boolean isLoanAvailable;

    private Boolean isReservationAvailable;

    private Boolean isOnline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImgPathThAttribute() {
        return imgPathThAttribute;
    }

    public void setImgPathThAttribute(String imgPathThAttribute) {
        this.imgPathThAttribute = imgPathThAttribute;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getNbCopy() {return nbCopy;}

    public void setNbCopy(int nbCopy) {this.nbCopy = nbCopy;}

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Boolean getLoanAvailable() {return isLoanAvailable;}

    public void setLoanAvailable(Boolean loanAvailable) {this.isLoanAvailable = loanAvailable;}

    public Boolean getReservationAvailable() {return isReservationAvailable;}

    public void setReservationAvailable(Boolean reservationAvailable) {this.isReservationAvailable = reservationAvailable;}

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        this.isOnline = online;
    }

}
