package com.library.mslibrary.api;

public interface ApiRegistration {

    String SERVICE_ID = "LIBRARY";

    String REST_PREFIX = "/ms-library";

    String REST_USERS = "/users";

    String REST_GET_USER_BY_EMAIL = "/findUserByEmail";

    String REST_GET_USER_BY_ID = "/getUserById";

    String REST_SAVE_USER = "/saveUser";

    String REST_SAVE_BOOK_LOAN = "/saveBookLoan";

    String REST_CREATE_BOOK_LOAN = "/createBookLoan";

    String REST_BOOKS_LIST = "/booksList";

    String REST_BOOK_BY_ID = "/findBookById";

    String REST_BOOK_BY_ISBN ="/getBookByIsbn";

    String REST_BOOK_LOANS_LIST = "/getBookLoansList";

    String REST_BOOK_RESERVATIONS_LIST = "/getBookReservationsList";

    String REST_BOOK_LOANS_LIST_BY_USER_ID = "/findBookLoansListByUserId";

    String REST_BOOK_RESERVATIONS_LIST_BY_USER_ID = "/findBookReservationsListByUserId";

    String REST_NB_CURRENT_BOOK_RESERVATIONS = "/getNbCurrentBookReservations";

    String REST_NB_CURRENT_BOOKLIST_RESERVATIONS = "/getNbCurrentBookListReservations";
    String REST_GET_NEXT_BOOKLOAN_ENDDATE = "/getNextBookloanEnddate";
    String REST_GET_NEXT_BOOKLOAN_ENDDATE_LIST = "/getNextBookloanEnddateList";

    String REST_BOOKS_LOANS = "/booksLoans";

    String REST_GET_BOOK_BY_ID = "/findBookById";

    String REST_PAGINATION = "/page";

    String REST_BOOK_LOANS_EXTEND = "/extendBookLoan";

    String REST_BOOK_LOANS_CLOSE = "/closeBookLoan";

    String REST_GET_BOOK_LOAN_BY_ID = "/bookLoan";

    String REST_GET_SEARCH_CRITERIA_LIST = "/getSearchCriteriaList";

    String REST_GET_ROLE_LIST ="/getRoleList";
}
