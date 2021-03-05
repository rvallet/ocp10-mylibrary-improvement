package com.library.mslibrary.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.service.BookReservationService;
import com.library.mslibrary.service.BookService;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    void createBookReservation(){

    }

    void closeBookReservation(){

    }

    void changeBookReservationStatusToNotified(){

    }

    void changeBookReservationStatusToExpired(){

    }

    void getBookReservationsList(){

    }
}
