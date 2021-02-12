package com.library.mslibrary.mock;

import com.library.mslibrary.entities.BookLoan;
import com.library.mslibrary.enumerated.BookLoanStatusEnum;
import com.library.mslibrary.utils.DateTools;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.library.mslibrary.mock.UserMock.*;
import static com.library.mslibrary.mock.BookMock.*;

public class BookLoanMock {

    private BookLoanMock(){

    }

    static final int bookLoanDuration = 30;

    public static BookLoan getMockBookLoan(){
        BookLoan bl = new BookLoan(
                getMockUser(),
                getMockBook(),
                bookLoanDuration
        );
        bl.setId(0L);
        return bl;
    }

    public static BookLoan getMockBookLoanExtendable(){
        BookLoan bl = new BookLoan(
                getMockUser(),
                getMockBook(),
                bookLoanDuration
        );
        bl.setId(0L);
        bl.setEndLoan(DateTools.addDays(new Date(), -1));
        bl.setLoanStatus(BookLoanStatusEnum.IN_PROGRESS.toString());
        return bl;
    }

    public static List<BookLoan> getMockBookLoanList(){
        return Arrays.asList(getMockBookLoan(),getMockBookLoan());
    }
}
