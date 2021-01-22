package com.library.mslibrary.repository;

import com.library.mslibrary.entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, String> {

    List<BookLoan> findAll();
    BookLoan findBookLoanById (Long id);

    /* SQL
        SELECT * FROM book_loan bl
        WHERE bl.id_user = 1;
     */
    //@Query("SELECT bl from BookLoan bl WHERE bl.user.id = ?1")
    List<BookLoan> findBookLoansByUserId (Long userId);

    /* SQL
        SELECT * FROM book_loan bl
        WHERE bl.id_book = 1;
    */
    List<BookLoan> findBookLoansByBookId (Long bookId);

    /* SQL
        SELECT bl.end_loan
        FROM library_bdd.book_loan AS bl
        WHERE id_book = 1
        ORDER BY bl.end_loan ASC
        LIMIT 1
        Native Query :
        value="SELECT 1 bl.end_loan from book_loan AS bl WHERE bl.book.id = ?1 ORDER BY bl.endLoan ASC LIMIT 1", nativeQuery = true
     */
    @Query("SELECT bl FROM BookLoan AS bl WHERE bl.book.id = ?1 ORDER BY bl.endLoan")
    List<BookLoan> findBookLoansByBookIdOrderByEndLoan(Long bookId);

}
