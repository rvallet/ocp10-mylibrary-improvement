package com.library.mslibrary.mock;

import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.enumerated.BookReservationStatusEnum;
import static com.library.mslibrary.mock.UserMock.*;
import static com.library.mslibrary.mock.BookMock.*;

import java.util.Arrays;
import java.util.List;

public class BookReservationMock {

    public static BookReservation getMockBookReservation(){
        BookReservation br = new BookReservation(getMockUser(), getMockBook());
        br.setId(0L);
        return br;
    }

    public static List<BookReservation> getMockBookReservationList(){
        return Arrays.asList(getMockBookReservation(), getMockBookReservation());
    }

    public static List<String> getMockCurrentBookReservationStatusList() {
        return Arrays.asList(
                BookReservationStatusEnum.IN_PROGRESS.toString(),
                BookReservationStatusEnum.NOTIFIED.toString()
        );
    }
}
