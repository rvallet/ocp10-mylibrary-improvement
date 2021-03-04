package com.library.mslibrary.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.mslibrary.api.ApiRegistration;
import com.library.mslibrary.entities.User;
import com.library.mslibrary.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.library.mslibrary.mock.UserMock.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getUsers() throws Exception {
        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_USERS)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(6))) // 6 users de Tests
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].*",hasSize(9))); // 9 attributs utilisateurs
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getUserByEmail() throws Exception {
        String email = "email@user2.fr";
        // @formatter:off
        mockMvc.perform(MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_USER_BY_EMAIL + "/" + email)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
        // @formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void getUserById() throws Exception {
        long userId = 2L;
        // @formatter:off
        mockMvc.perform(MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_USER_BY_ID + "/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId));
        // @formatter:on

    }

    @Test
    void getRoleList() throws Exception {
        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders
                .get(ApiRegistration.REST_GET_ROLE_LIST)
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
    void saveUser_UpdatedField() throws Exception {
        User dbUser = userService.findUserById(2L);
        dbUser.setFirstName("UpdatedUser");

        // @formatter:off
        mockMvc.perform(MockMvcRequestBuilders
                .post(ApiRegistration.REST_SAVE_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(dbUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        User resultUser = userService.findUserById(2L);

        Assertions.assertEquals(
                dbUser.getFirstName(),
                resultUser.getFirstName(),
                "UpdatedUser"
        );

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/init_db.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/clean_db.sql")
    void saveUser_NewUser() throws Exception {
        User user = getMockUser();
        String email = "saveduser@junit.test";
        user.setEmail(email);

        // @formatter:off
        mockMvc.perform(MockMvcRequestBuilders
                .post(ApiRegistration.REST_SAVE_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // @formatter:on

        User resultUser = userService.findUserByEmail(email);

        Assertions.assertEquals(
                email,
                resultUser.getEmail(),
                "new user email"
        );

        Assertions.assertEquals(
                7L,
                resultUser.getId(),
                "new user id"
        );

    }
}
