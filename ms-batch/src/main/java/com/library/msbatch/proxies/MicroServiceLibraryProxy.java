package com.library.msbatch.proxies;

import com.library.msbatch.beans.BookLoanBean;
import com.library.msbatch.beans.BookReservationBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-library")
@RibbonClient(name = "ms-library")
public interface MicroServiceLibraryProxy {

    @GetMapping(value= "/getBookLoansList")
    List<BookLoanBean> getBookLoansList();

    @GetMapping(value="/getBookReservationsList/{bookId}")
    List<BookReservationBean> getBookReservationsList(@PathVariable Long bookId);

    @GetMapping(value="/closeBookReservation/{bookReservationId}")
    BookReservationBean closeBookReservation(@PathVariable Long bookReservationId);

    @GetMapping(value="/changeBookReservationStatusToNotified/{bookReservationId}")
    void changeBookReservationStatusToNotified(@PathVariable Long bookReservationId);

    @GetMapping(value="/changeBookReservationStatusToExpired/{bookReservationId}")
    void changeBookReservationStatusToExpired(@PathVariable Long bookReservationId);



}
