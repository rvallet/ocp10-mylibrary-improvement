package com.library.website.proxies;

import com.library.website.beans.BookBean;
import com.library.website.beans.BookLoanBean;
import com.library.website.beans.BookReservationBean;
import com.library.website.beans.UserBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-library")
@RibbonClient(name = "ms-library")
public interface MicroServiceLibraryProxy {

    @GetMapping(value= "/users")
    List<UserBean> getUsers();

    @GetMapping(value= "/users/page/{pageNumber}/{pageSize}")
    Page<UserBean> getPaginatedUsers(@PathVariable int pageNumber, @PathVariable int pageSize);

    @GetMapping(value= "/booksList")
    List<BookBean> getBookList();

    @GetMapping(value= "/getBookByIsbn/{isbn}")
    BookBean getBookByIsbn(@PathVariable String isbn);

    @GetMapping(value= "/getUserById/{id}")
    UserBean getUserById(@PathVariable Long id);

    @GetMapping(value= "/findBookById/{id}")
    BookBean getBookById(@PathVariable Long id);

    @GetMapping(value= "/bookLoan/{bookLoanId}")
    BookLoanBean getBookLoanById(@PathVariable Long bookLoanId);

    @GetMapping(value= "/findBookLoansListByUserId/{userId}")
    List<BookLoanBean> getBookLoansByUserId(@PathVariable String userId);

    @GetMapping(value= "/getBookLoansList")
    List<BookLoanBean> getBookLoansList();

    @GetMapping(value= "/getBookReservationsList")
    List<BookReservationBean> getBookReservationsList();

    @GetMapping(value= "/findBookReservationsListByUserId/{userId}")
    List<BookReservationBean> getBookReservationsByUserId(@PathVariable String userId);

    @GetMapping(value = "/userDetails")
    UserDetails getUserDetails() ;

    @GetMapping(value = "/findUserByEmail/{email}")
    UserBean getUserByEmail(@PathVariable String email);

    @PostMapping(value = "/saveUser")
    UserBean saveUser(@RequestBody UserBean user);

    @PostMapping(value = "/saveBook")
    BookBean saveBook(@RequestBody BookBean book);

    @PostMapping(value = "/saveBookLoan")
    BookLoanBean saveBookLoan(@RequestBody BookLoanBean bookLoanBean);

    @PostMapping(value = "/createBookLoan")
    BookLoanBean createBookLoan(@RequestBody BookLoanBean bookLoanBean);

    @PostMapping(value = "/createBookReservation")
    BookLoanBean createBookReservation(@RequestBody BookReservationBean bookReservationBean);

    @GetMapping(value = "/getNbCurrentBookReservations")
    BookLoanBean getNbCurrentBookReservation(@PathVariable Long bookId);

    @PostMapping(value = "/getNbCurrentBookListReservations")
    Map<Integer, Integer> getNbCurrentBookListReservation(@RequestBody List<BookBean> bookList);

    @GetMapping(value = "/extendBookLoan/{bookLoanId}")
    BookLoanBean extendBookLoan(@PathVariable Long bookLoanId);

    @GetMapping(value = "/closeBookLoan/{bookLoanId}")
    BookLoanBean closeBookLoan(@PathVariable Long bookLoanId);

    @GetMapping(value="/getSearchCriteriaList")
    List<String> getSearchCriteriaList();

    @GetMapping(value="/getRoleList")
    List<String> getRoleList();
}
