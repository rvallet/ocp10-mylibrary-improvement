package com.library.mslibrary.service.impl;

import static com.library.mslibrary.mock.BookMock.*;
import static com.library.mslibrary.mock.BookLoanMock.*;

import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookLoan;
import com.library.mslibrary.enumerated.BookLoanStatusEnum;
import com.library.mslibrary.repository.BookLoanRepository;
import com.library.mslibrary.utils.DateTools;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class BookLoanServiceImplTest {

    @Mock
    private BookLoanRepository bookLoanRepository;

    @Mock
    private ApplicationPropertiesConfig appConfig;

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
                bookLoanService.findBookLoansByUserId(1L).size(),
                "Liste des emprunts de livres par userId"
        );
    }

    @Test
    void saveAll(){
        List<BookLoan> bookLoanList = getMockBookLoanList();

        given(bookLoanRepository.saveAll(anyIterable())).willReturn(bookLoanList);

        Assertions.assertEquals(
                bookLoanList.size(),
                bookLoanService.saveAll(bookLoanList).size(),
                "Nombre d'emprunts enregistrés");
    }

    @Test
    void saveBookLoan(){
        BookLoan bookLoan = getMockBookLoan();

        given(bookLoanRepository.save(bookLoan)).willReturn(bookLoan);

        Assertions.assertEquals(
                bookLoan.getId(),
                bookLoanService.saveBookLoan(bookLoan).getId(),
                "Enregistrement d'un emprunt de livre"
        );
    }

    @Test
    void getNextBookloanEndDate(){
        Book book = getMockBook();
        book.setId(1L);

        BookLoan bl1 = getMockBookLoan();
        BookLoan bl2 = getMockBookLoan();
        bl1.setBook(book);
        bl2.setBook(book);

        Date todayPlusOne = DateTools.addDays(new Date(), 1);
        Date todayPlusTwo = DateTools.addDays(new Date(), 2);
        String expectedEndDate = DateTools.dateFormat(todayPlusOne);

        bl1.setEndLoan(todayPlusOne);
        bl2.setEndLoan(todayPlusTwo);

        List<BookLoan> blList = Arrays.asList(bl1, bl2);

        given(bookLoanRepository.findBookLoansByBookIdOrderByEndLoan(anyLong())).willReturn(blList);

        Assertions.assertEquals(
                expectedEndDate,
                bookLoanService.getNextBookloanEndDate(book.getId()),
                "Date de fin d'emprunt"
        );

    }

    @Test
    void closeBookLoan(){
        BookLoan bl = getMockBookLoan();

        given(bookLoanRepository.findBookLoanById(anyLong())).willReturn(bl);
        given(bookLoanRepository.save(Mockito.any(BookLoan.class))).willReturn(bl);

        BookLoan result = bookLoanService.closeBookLoan(bl.getId());
        Assertions.assertEquals(
                new Date().toString(),
                result.getReturnLoan().toString(),
                "Date de retour d'emprunt");
        Assertions.assertEquals(
                1,
                result.getBook().getStock(),
                "Incrémentation du stock sur le livre rendu");
        Assertions.assertTrue(
                result.getBook().getLoanAvailable(),
                "Passage du livre en disponible à l'emprunt");
        Assertions.assertEquals(
                BookLoanStatusEnum.CLOSED.toString(),
                result.getLoanStatus(),
                "Passage du Statut de l'emprunt à CLOSED");
        Assertions.assertTrue(
                result.getBook().getReservationAvailable(),
                "Passage du livre en disponible à la réservation"
        );
    }

    @Test
    void extendBookLoan_OK(){
        BookLoan bl = getMockBookLoanExtendable();
        bl.setId(1L);

        given(bookLoanRepository.findBookLoanById(anyLong())).willReturn(bl);
        given(appConfig.getBookLoanDuration()).willReturn(30);

        BookLoan result = bookLoanService.extendBookLoan(1L);
        Assertions.assertTrue(
                result.getLoanExtended(),
                "Prolongation de l'emprunt");
    }

    @Test
    void extendBookLoan_KO(){
        BookLoan bl = getMockBookLoanExtendable();
        bl.setId(1L);
        bl.setEndLoan(DateTools.addDays(new Date(), -1));

        given(bookLoanRepository.findBookLoanById(anyLong())).willReturn(bl);
        given(appConfig.getBookLoanDuration()).willReturn(30);

        BookLoan result = bookLoanService.extendBookLoan(1L);
        Assertions.assertFalse(
                result.getLoanExtended(),
                "Prolongation de l'emprunt");
    }
}