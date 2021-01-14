package com.library.mslibrary.repository;

import com.library.mslibrary.entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, String> {

    List<BookLoan> findAll();
    BookLoan findBookLoanById (Long id);

    /*
        SELECT * FROM book_loan bl
        WHERE bl.id_user = 1;
     */
    //@Query("SELECT bl from BookLoan bl WHERE bl.user.id = ?1")
    List<BookLoan> findBookLoansByUserId (Long userId);

}
