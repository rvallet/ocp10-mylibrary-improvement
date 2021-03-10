package com.library.mslibrary.ws.controller;

import com.library.mslibrary.api.ApiRegistration;
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

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getBookList() throws Exception {
        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOKS_LIST)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(10))) // 10 livres Tests
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].*",hasSize(15))); // 15 attributs sur chaque livres
        // @formatter:on
    }

    @Test
    void getSearchCriteriaList() throws Exception {
        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_SEARCH_CRITERIA_LIST)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andDo(print());
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getBookById() throws Exception {
        long bookId = 2L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_BOOK_BY_ID + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId));
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getBookById_KO() throws Exception {
        long bookId = 0L;

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_BOOK_BY_ID + "/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getBookByIsbn() throws Exception {
        String isbn = "isbn2";

        // @formatter:off
        mockMvc.perform(MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_BY_ISBN + "/" + isbn)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(isbn));
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getBookByIsbn_KO() throws Exception {
        String isbn = "isbn0";

        // @formatter:off
        mockMvc.perform(MockMvcRequestBuilders
                .get(ApiRegistration.REST_BOOK_BY_ISBN + "/" + isbn)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").doesNotExist());
        // @formatter:on
    }

}