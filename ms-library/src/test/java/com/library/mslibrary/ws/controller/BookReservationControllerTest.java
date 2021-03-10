package com.library.mslibrary.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.entities.User;
import com.library.mslibrary.enumerated.BookReservationStatusEnum;
import com.library.mslibrary.service.BookReservationService;
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
import static com.library.mslibrary.mock.BookReservationMock.*;
import static com.library.mslibrary.mock.BookMock.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookReservationService bookReservationService;

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
    void findBookReservationsListByUserId() throws Exception {
        final long userId = 2L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_RESERVATIONS_LIST_BY_USER_ID + "/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2))) // liste de 2 réservations
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.id").value(userId)); // correspond au userId demandé
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void findBookReservationsListByUserId_KO() throws Exception {
        final long userId = 0L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_RESERVATIONS_LIST_BY_USER_ID + "/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").doesNotExist());
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void findBookReservationsList() throws Exception {
        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_RESERVATIONS_LIST)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(4))); // liste de 4 réservations
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getNbCurrentBookReservations() throws Exception {
        final long bookId = 2L;
        final String expectedNbBookReservation = "2";

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_NB_CURRENT_BOOK_RESERVATIONS + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedNbBookReservation));
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getNbCurrentBookReservations_None() throws Exception {
        final long bookId = 10L;
        final String expectedNbBookReservation = "0";

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_NB_CURRENT_BOOK_RESERVATIONS + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedNbBookReservation));
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getNbCurrentBookReservations_UnexistingBook() throws Exception {
        final long bookId = 0L;
        final String expectedNbBookReservation = "0";

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_NB_CURRENT_BOOK_RESERVATIONS + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedNbBookReservation));
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Transactional
    void getNbCurrentBookListReservations() throws Exception {
        final long bookId1 = 1L;
        final long bookId2 = 2L;
        final int nbReservationBook1 = 2;
        final int nbReservationBook2 = 2;

        List<Book> bookList = Arrays.asList(
                bookService.findBookById(bookId1),
                bookService.findBookById(bookId2));

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .post(ApiRegistration.REST_NB_CURRENT_BOOKLIST_RESERVATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bookList))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2))) // Map<Integer, Integer> avec 2 entrées
                .andExpect(MockMvcResultMatchers.jsonPath("$.1").value(nbReservationBook1)) // 2 réservations active livre 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.2").value(nbReservationBook2)); // 2 réservations active livre 2
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Transactional
    void getNbCurrentBookListReservations_UnexistingBook() throws Exception {
        Book book = getMockBook();
        List<Book> bookList = Arrays.asList(book);
        final int nbReservationBook = 0;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .post(ApiRegistration.REST_NB_CURRENT_BOOKLIST_RESERVATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bookList))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1))) // Map<Integer, Integer> avec 1 entrée
                .andExpect(MockMvcResultMatchers.jsonPath("$.0").value(nbReservationBook)); // 0 réservations active livre 0
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Transactional
    void getUserPositionInBookReservation() throws Exception {
        final long bookReservationId = 1L;
        final String expectedPosition = "2";
        BookReservation bookReservation = bookReservationService.findBookReservationById(bookReservationId);

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .post(ApiRegistration.REST_GET_USER_POSITION_IN_BOOK_RESERVATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bookReservation))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedPosition)); // position 2
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Transactional
    void getUserPositionsListInBookReservation() throws Exception{
        final long userId = 2L;
        final String expectedPosition1 = "1";
        final String expectedPosition2 = "2";

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_USER_POSITIONS_LIST_IN_BOOK_RESERVATIONS + "/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2))) // Map<Integer, Integer> avec 2 entrées
                .andExpect(MockMvcResultMatchers.jsonPath("$.1").value(expectedPosition1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.2").value(expectedPosition2));
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Transactional
    void createBookReservation() throws Exception{
        final long bookId = 1L;
        final long userId = 1L;
        final long expectedBookReservationId = 7L;
        final Date today = new Date();
        final Book dbBook = bookService.findBookById(bookId);
        final User dbUser = userService.findUserById(userId);
        BookReservation bookReservation = getMockBookReservation();
        bookReservation.setBook(dbBook);
        bookReservation.setUser(dbUser);

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .post(ApiRegistration.REST_CREATE_BOOK_RESERVATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bookReservation))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        BookReservation createdBookReservation = bookReservationService.findBookReservationById(expectedBookReservationId);

        if (createdBookReservation != null) {
            Assertions.assertEquals(
                    expectedBookReservationId,
                    createdBookReservation.getId(),
                    "BookReservation id = "+expectedBookReservationId
            );

            Assertions.assertTrue(
                    DateTools.dateFormat(today).equals(DateTools.dateFormat(createdBookReservation.getCreationDate())),
                    "Date de création"
            );

            Assertions.assertEquals(
                    BookReservationStatusEnum.IN_PROGRESS.toString(),
                    createdBookReservation.getReservationStatus(),
                    "Status de la réservation"
            );

        } else {throw new Exception("BookReservation id "+expectedBookReservationId+" non créé");}


    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void closeBookReservation() throws Exception {
        final long bookReservationId = 1L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_CLOSE_BOOK_RESERVATION + "/" + bookReservationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookReservationId)) // Match avec la reservation demandée
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationStatus").value(BookReservationStatusEnum.CLOSED.toString())) // MaJ Statut
                .andExpect(MockMvcResultMatchers.jsonPath("$.closingDate").exists()); // MaJ date de clôture
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void closeBookReservation_KO() throws Exception {
        final long bookReservationId = 0L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_CLOSE_BOOK_RESERVATION + "/" + bookReservationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").doesNotExist());
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void changeBookReservationStatusToNotified() throws Exception {
        final long bookReservationId = 1L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_CHANGE_BOOK_RESERVATION_TO_NOTIFIED + "/" + bookReservationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        BookReservation result = bookReservationService.findBookReservationById(bookReservationId);

        Assertions.assertEquals(
                BookReservationStatusEnum.NOTIFIED.toString(),
                result.getReservationStatus(),
                "Mise à jour du statut"
        );

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void changeBookReservationStatusToNotified_KO() throws Exception {
        final long bookReservationId = 0L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_CHANGE_BOOK_RESERVATION_TO_NOTIFIED + "/" + bookReservationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").doesNotExist());
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void changeBookReservationStatusToExpired() throws Exception {
        final long bookReservationId = 1L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_CHANGE_BOOK_RESERVATION_TO_EXPIRED + "/" + bookReservationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        BookReservation result = bookReservationService.findBookReservationById(bookReservationId);

        Assertions.assertEquals(
                BookReservationStatusEnum.EXPIRED.toString(),
                result.getReservationStatus(),
                "Mise à jour du statut"
        );
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void changeBookReservationStatusToExpired_KO() throws Exception {
        final long bookReservationId = 0L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_CHANGE_BOOK_RESERVATION_TO_EXPIRED + "/" + bookReservationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").doesNotExist());
        // @formatter:on

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getBookReservationsList() throws Exception{
        final long bookId = 1L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_BOOK_RESERVATIONS_LIST + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2)));
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getBookReservationsList_KO() throws Exception{
        final long bookId = 0L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_BOOK_RESERVATIONS_LIST + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(0)));
        // @formatter:on
    }
}
