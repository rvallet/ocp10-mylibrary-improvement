package com.library.mslibrary.enumerated;

public enum BookLoanStatusEnum {
    IN_PROGRESS ("En cours"),
    EXTENDED ("Prolongé"),
    CLOSED ("Terminé");

    private String bookLoanStatus;

    BookLoanStatusEnum(String bookLoanStatus) {
        this.bookLoanStatus = bookLoanStatus;
    }

    @Override
    public String toString() {
        return bookLoanStatus;
    }
}
