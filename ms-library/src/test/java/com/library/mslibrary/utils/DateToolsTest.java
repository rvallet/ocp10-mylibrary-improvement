package com.library.mslibrary.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
public class DateToolsTest {

    @Test
    void addDays(){
        Calendar cal = Calendar.getInstance();
        cal.set(2000,Calendar.NOVEMBER,01);

        final Date datePlusOneDay = DateTools.addDays(cal.getTime(), 1);

        cal.set(2000, Calendar.NOVEMBER, 02);
        final Date expectedDate = cal.getTime();

        Assertions.assertEquals(
                expectedDate,
                datePlusOneDay,
                "Ajout d'un jour à la date"
        );
    }

    @Test
    void dateFormat(){
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.NOVEMBER,01);

        String expected = "01/11/2000";

        Assertions.assertEquals(
                expected,
                DateTools.dateFormat(cal.getTime()),
                "Format de Date dd/mm/aaaa"
        );

    }
}
