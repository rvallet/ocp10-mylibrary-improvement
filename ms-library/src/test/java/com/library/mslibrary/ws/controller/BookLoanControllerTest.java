package com.library.mslibrary.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookLoan;
import com.library.mslibrary.entities.User;
import com.library.mslibrary.enumerated.BookLoanStatusEnum;
import com.library.mslibrary.service.BookLoanService;
import com.library.mslibrary.service.BookService;
import com.library.mslibrary.service.UserService;
import com.library.mslibrary.utils.DateTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.library.mslibrary.mock.BookLoanMock.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookLoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookLoanService bookLoanService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void findBookLoansListByUserId() throws Exception {
        final long userId = 2L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_LOANS_LIST_BY_USER_ID + "/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2))) // liste de 2 emprunts
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.id").value(userId)); // correspond au userId demandé
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void findBookLoansList() throws Exception {
        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_LOANS_LIST)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(6))); // liste de 6 emprunts
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void extendBookLoan() throws Exception {
        final long bookLoanId = 3L;

        BookLoan dbBookLoan = bookLoanService.findBookLoanById(bookLoanId);

        // On met une date de fin d'emprunt après aujourd'hui afin de pouvoir prolonger l'emprunt
        dbBookLoan.setEndLoan(DateTools.addDays(new Date(), 1));
        bookLoanService.saveBookLoan(dbBookLoan);

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_LOANS_EXTEND + "/" + bookLoanId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookLoanId)); // correspond au bookLoanId demandé
        // @formatter:on

        BookLoan updatedBookLoan = bookLoanService.findBookLoanById(bookLoanId);

        Assertions.assertFalse(
                dbBookLoan.getLoanExtended(),
                "L'emprunt n'a pas déjà été prolongé");
        Assertions.assertTrue(
                updatedBookLoan.getLoanExtended(),
                "L'emprunt viens d'être prolongé");

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void closeBookLoan() throws Exception {
        final long bookLoanId = 1L;

        Assertions.assertTrue(BookLoanStatusEnum.IN_PROGRESS.toString().equals(bookLoanService.findBookLoanById(bookLoanId).getLoanStatus()));

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_LOANS_CLOSE + "/" + bookLoanId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookLoanId)) // correspond au bookLoanId demandé
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanStatus").value(BookLoanStatusEnum.CLOSED.toString())); // passage en statut fermé
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void findBookLoanById() throws Exception {
        final long bookLoanId = 6L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_BOOK_LOAN_BY_ID + "/" + bookLoanId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookLoanId)); // correspond au bookLoanId demandé
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void saveBookLoan() throws Exception {
        final long bookLoanId = 1L;
        final Book dbBook = bookService.findBookById(1L);
        final User dbUser = userService.findUserById(1L);
        final Date today = new Date();

        BookLoan initialDbBookloan = bookLoanService.findBookLoanById(bookLoanId);

        initialDbBookloan.setLoanStatus(BookLoanStatusEnum.EXTENDED.toString());
        initialDbBookloan.setStartLoan(today);
        initialDbBookloan.setEndLoan(today);
        initialDbBookloan.setReturnLoan(today);
        initialDbBookloan.setLoanExtended(true);
        initialDbBookloan.setBook(dbBook);
        initialDbBookloan.setUser(dbUser);

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .post(ApiRegistration.REST_SAVE_BOOK_LOAN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(initialDbBookloan))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        BookLoan resultDbBookloan = bookLoanService.findBookLoanById(bookLoanId);

        Assertions.assertEquals(
                initialDbBookloan.getId(),
                resultDbBookloan.getId(),
                "Id de l'emprunt inchangé");

        Assertions.assertTrue(
                resultDbBookloan.getLoanExtended(),
                "Mise à jour de la prolongation l'emprunt");

        Assertions.assertEquals(
                BookLoanStatusEnum.EXTENDED.toString(),
                resultDbBookloan.getLoanStatus(),
                "Mise à jour du statut de l'emprunt");

        Assertions.assertTrue(
                today.compareTo(resultDbBookloan.getStartLoan()) == 0,
                "Mise à jour de la date de début de l'emprunt");

        Assertions.assertTrue(
                today.compareTo(resultDbBookloan.getEndLoan()) == 0,
                "Mise à jour de la date de din de l'emprunt");

        Assertions.assertTrue(
                today.compareTo(resultDbBookloan.getReturnLoan()) == 0,
                "Mise à jour  de la date de retour de l'emprunt");

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Transactional
    void createBookLoan() throws Exception{
        final long bookId = 1L;
        final long userId = 1L;
        final long expectedBookLoanId = 7L;
        final Book dbBook = bookService.findBookById(bookId);
        final User dbUser = userService.findUserById(userId);
        BookLoan bookloan = getMockBookLoan();
        bookloan.setBook(dbBook);
        bookloan.setUser(dbUser);

        List<BookLoan> initalBookLoanList = bookLoanService.findBookLoansByBookId(bookId);

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .post(ApiRegistration.REST_CREATE_BOOK_LOAN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bookloan))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        List<BookLoan> resultBookLoanList = bookLoanService.findBookLoansByBookId(bookId);

        Assertions.assertTrue(
                initalBookLoanList.size() == resultBookLoanList.size() -1,
                "Le nombre d'emprunt en BDD à augmenté de 1 pour ce livre");

        BookLoan createdBookLoan = bookLoanService.findBookLoanById(expectedBookLoanId);

        if (createdBookLoan != null) {
            Assertions.assertEquals(
                    expectedBookLoanId,
                    createdBookLoan.getId(),
                    "BookLoan id = "+expectedBookLoanId
            );

            Assertions.assertEquals(
                    0,
                    createdBookLoan.getBook().getStock(),
                    "Mise à jour du stock "
            );


        } else {throw new Exception("BookLoan id "+expectedBookLoanId+" non créé");}

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getNextBookloanEndDate() throws Exception {
        final long bookId = 1L;
        final String expectedDate = "10/02/2021";

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_NEXT_BOOKLOAN_ENDDATE + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedDate)); // correspond à la date la plus récente
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getNextBookloanEnddateList() throws Exception {
        final long bookId1 = 1L;
        final long bookId2 = 2L;
        final String expectedDateBook1 = "10/02/2021";
        final String expectedDateBook2 = "23/02/2021";

        List<Book> bookList = Arrays.asList(
                bookService.findBookById(bookId1),
                bookService.findBookById(bookId2));

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .post(ApiRegistration.REST_GET_NEXT_BOOKLOAN_ENDDATE_LIST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bookList))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2))) // Map<Integer, String> avec 2 entrées
                .andExpect(MockMvcResultMatchers.jsonPath("$.1").value(expectedDateBook1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.2").value(expectedDateBook2));
        // @formatter:on

    }
}