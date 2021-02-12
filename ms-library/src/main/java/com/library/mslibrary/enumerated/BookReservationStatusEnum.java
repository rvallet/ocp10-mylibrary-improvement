package com.library.mslibrary.enumerated;

public enum BookReservationStatusEnum {
    IN_PROGRESS ("En cours"),
    NOTIFIED ("Notification envoyée"),
    EXPIRED ("Expirée"),
    CLOSED ("Terminé");

    private String bookReservationStatus;

    BookReservationStatusEnum(String bookReservationStatus) {
        this.bookReservationStatus = bookReservationStatus;
    }

    @Override
    public String toString() {
        return bookReservationStatus;
    }

}

