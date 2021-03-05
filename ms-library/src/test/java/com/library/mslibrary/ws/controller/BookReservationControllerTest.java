package com.library.mslibrary.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    void findBookReservationsListByUserId() {

    }

    void findBookReservationsList() {

    }

    void getNbCurrentBookReservations(){

    }

    void getNbCurrentBookListReservations(){

    }

    void getUserPositionInBookReservation(){

    }

    void getUserPositionsListInBookReservation(){

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
