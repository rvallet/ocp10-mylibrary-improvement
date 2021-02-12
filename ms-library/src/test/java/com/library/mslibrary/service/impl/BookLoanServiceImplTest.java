package com.library.mslibrary.service.impl;

import static com.library.mslibrary.mock.UserMock.*;
import static com.library.mslibrary.mock.BookMock.*;
import static com.library.mslibrary.mock.BookLoanMock.*;
import com.library.mslibrary.entities.BookLoan;
import com.library.mslibrary.repository.BookLoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class BookLoanServiceImplTest {

    @Mock
    private BookLoanRepository bookLoanRepository;

    @InjectMocks
    private BookLoanServiceImpl bookLoanService;

    @Test
    void findAllBookLoan(){
        List<BookLoan> bookLoanList = getMockBookLoanList();

        given(bookLoanRepository.findAll()).willReturn(bookLoanList);

        Assertions.assertEquals(
                bookLoanList.size(),
                bookLoanService.findAll().size(),
                "Liste complète des emprunts de livres"
        );
    }

    @Test
    void findBookLoanById() {
        BookLoan bl = getMockBookLoan();

        given(bookLoanRepository.findBookLoanById(anyLong())).willReturn(bl);
        //doReturn(bl).when(bookLoanRepository).findBookLoanById(anyLong());
        Assertions.assertEquals(
                bl.getId(),
                bookLoanService.findBookLoanById(bl.getId()).getId(),
                "Recherche d'emprunt par Id"
        );
    }

    @Test
    void findBookLoansByUserId(){
        List<BookLoan> bookLoanList = getMockBookLoanList();

        given(bookLoanRepository.findBookLoansByUserId(anyLong())).willReturn(bookLoanList);

        Assertions.assertEquals(
                bookLoanList.size(),
                bookLoanService.findAll().size(),
                "Liste des emprunts de livres par userId"
        );
    }

    @Test
    void saveBookLoan(){
        BookLoan bookLoan = getMockBookLoan();

        given(bookLoanRepository.save(bookLoan)).willReturn(bookLoan);

        Assertions.assertEquals(
                bookLoan.getId(),
                bookLoanService.findBookLoanById(bookLoan.getId()).getId(),
                "Enregistrement d'un emprunt"
        );

    }

    @Test
    void extendBookLoan(){
        BookLoan bl1 = getMockBookLoanExtendable();
        BookLoan bl2 = getMockBookLoan();

        bookLoanService.extendBookLoan(bl1.getId());
        //Assertions.assertTrue(bl1.getLoanExtended());
        //Assertions.assertNotEquals(0, bl1.getLoanExtended().compareTo(bl2.getLoanExtended()));

    }

}
