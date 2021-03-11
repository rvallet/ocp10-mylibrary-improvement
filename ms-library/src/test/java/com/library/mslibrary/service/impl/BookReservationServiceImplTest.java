package com.library.mslibrary.service.impl;

import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.Book;
import com.library.mslibrary.entities.BookReservation;
import com.library.mslibrary.enumerated.BookReservationStatusEnum;
import com.library.mslibrary.repository.BookRepository;
import com.library.mslibrary.repository.BookReservationRepository;

import static com.library.mslibrary.mock.BookReservationMock.*;
import static com.library.mslibrary.mock.BookMock.*;

import com.library.mslibrary.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
public class BookReservationServiceImplTest {

    @Mock
    BookReservationRepository bookReservationRepository;

    @Mock
    BookService bookService;

    @Mock
    private ApplicationPropertiesConfig appConfig;

    @InjectMocks
    BookReservationServiceImpl bookReservationService;

    @Test
    void findBookReservationById(){
        BookReservation br = getMockBookReservation();
        br.setId(1L);

        given(bookReservationRepository.findBookReservationById(anyLong())).willReturn(br);

        Assertions.assertEquals(
                br,
                bookReservationService.findBookReservationById(br.getId()),
                "Recherche de réservation par bookId"
        );

    }

    @Test
    void findBookReservationsByUserId(){
        final long userId = 1;
        BookReservation br = getMockBookReservation();
        br.getUser().setId(userId);

        given(bookReservationRepository.findBookReservationsByUserIdAndFilteredByStatusList(anyLong(), eq(getMockCurrentBookReservationStatusList()))).willReturn(Collections.singletonList(br));

        List<BookReservation> brList = bookReservationService.findBookReservationsByUserId(userId);

        Assertions.assertEquals(
                1,
                brList.size(),
                "Liste des réservations par utilisateur"
        );
        Assertions.assertTrue(
                brList.stream().allMatch(o -> o.getUser().getId() == userId),
                "Id du livre"
        );
    }

    @Test
    void findBookReservationsByBookId(){
        final long bookId = 1;
        BookReservation br = getMockBookReservation();
        br.getBook().setId(bookId);

        given(bookReservationRepository.findBookReservationsByBookIdAndFilteredByStatusList(anyLong(), eq(getMockCurrentBookReservationStatusList()))).willReturn(Collections.singletonList(br));

        List<BookReservation> brList = bookReservationService.findBookReservationsByBookId(bookId);

        Assertions.assertEquals(
                1,
                brList.size(),
                "Liste des réservations par livre"
        );
        Assertions.assertTrue(
                brList.stream().allMatch(o -> o.getBook().getId() == bookId),
                "Id du livre"
        );
    }

    @Test
    void saveBookReservation(){
        BookReservation br = getMockBookReservation();

        given(bookReservationRepository.save(any(BookReservation.class))).willReturn(br);

        Assertions.assertEquals(
            br.getId(),
            bookReservationService.saveBookReservation(br).getId(),
            "Enregistrement d'une réservation de livre"
        );
    }

    @Test
    @Transactional
    void closeBookReservation(){
        BookReservation br = getMockBookReservation();

        given(bookReservationRepository.findBookReservationById(anyLong())).willReturn(br);
        given(bookReservationRepository.save(Mockito.any(BookReservation.class))).willReturn(br);
        given(bookService.saveBook(any(Book.class))).willReturn(br.getBook());
        given(appConfig.getBookReservationFactorLimit()).willReturn(2);

        BookReservation result = bookReservationService.closeBookReservation(br.getId());

        Assertions.assertEquals(
               br.getId(),
               result.getId(),
               "Réservation id"
        );
        Assertions.assertEquals(
                new Date().toString(),
                result.getClosingDate().toString(),
                "Date de cloture de la réservation"
        );
        Assertions.assertEquals(
                BookReservationStatusEnum.CLOSED.toString(),
                result.getReservationStatus(),
                "Passage du statut de la réservation à CLOSED"
        );
    }

    @Test
    void changeBookReservationStatus(){
        BookReservation br = getMockBookReservation();
        br.setReservationStatus(BookReservationStatusEnum.IN_PROGRESS.toString());

        given(bookReservationRepository.findBookReservationById(anyLong())).willReturn(br);

        bookReservationService.changeBookReservationStatus(br.getId(), BookReservationStatusEnum.NOTIFIED.toString());
        Assertions.assertEquals(
                BookReservationStatusEnum.NOTIFIED.toString(),
                br.getReservationStatus(),
                "Modification du statut en NOTIFIED"
        );
        Assertions.assertEquals(
                new Date().toString(),
                br.getNotificationDate().toString(),
                "Date de notification de la réservation"
        );

        bookReservationService.changeBookReservationStatus(br.getId(), BookReservationStatusEnum.CLOSED.toString());
        Assertions.assertEquals(
                BookReservationStatusEnum.CLOSED.toString(),
                br.getReservationStatus(),
                "Modification du statut en CLOSED"
        );
        Assertions.assertEquals(
                new Date().toString(),
                br.getClosingDate().toString(),
                "Date de cloture de la réservation"
        );

    }

    @Test
    void saveAll(){
        List<BookReservation> brList = getMockBookReservationList();

        given(bookReservationRepository.saveAll(anyIterable())).willReturn(brList);

        Assertions.assertEquals(
                brList.size(),
                bookReservationService.saveAll(brList).size(),
                "Nombre de reservations enregistrées");
    }

    @Test
    void nbBookReservation(){
        BookReservation br = getMockBookReservation();

        given(bookReservationRepository.countBookReservationByBookAndFilteredByStatusList(any(Book.class), anyList())).willReturn(anyInt());

        Assertions.assertTrue(
                bookReservationService.nbBookReservation(br.getBook(), eq(getMockCurrentBookReservationStatusList())) instanceof Integer,
                "Nombre de réservations sur un livre"
        );
    }

    @Test
    void findBookReservationByUserIdAndByBookId(){
        long idUser = 1;
        long idBook = 2;
        BookReservation br = getMockBookReservation();
        br.getBook().setId(idBook);
        br.getUser().setId(idUser);

        given(bookReservationRepository.findBookReservationsByUserIdAndByBookIdAndFilteredByStatusList(anyLong(), anyLong(), eq(getMockCurrentBookReservationStatusList()))).willReturn(br);

        Assertions.assertEquals(
                idUser,
                bookReservationService.findBookReservationByUserIdAndByBookId(br.getUser().getId(), br.getBook().getId()).getUser().getId(),
                "Id de l'utilisateur"
        );
        Assertions.assertEquals(
                idBook,
                bookReservationService.findBookReservationByUserIdAndByBookId(br.getUser().getId(), br.getBook().getId()).getBook().getId(),
                "Id du livre"
        );
    }

    @Test
    void computePositionOfBookReservation(){
        BookReservation br1 = getMockBookReservation();
        BookReservation br2 = getMockBookReservation();
        br1.getUser().setId(1L);
        br2.getUser().setId(2L);

        List<BookReservation> brList = Arrays.asList(br1, br2);
        given(bookReservationRepository.findBookReservationsByBookIdAndFilteredByStatusList(anyLong(), eq(getMockCurrentBookReservationStatusList()))).willReturn(brList);

        Assertions.assertEquals(
                1,
                bookReservationService.computePositionOfBookReservation(br1),
                "Réservation n°1 en position 1"
        );

        Assertions.assertEquals(
                2,
                bookReservationService.computePositionOfBookReservation(br2),
                "Réservation n°2 en position 2"
        );
    }

    @Test
    void computeIsReservationAvailable(){
        Book book = getMockBook();

        given(appConfig.getBookReservationFactorLimit()).willReturn(2);
        given(bookReservationRepository.countBookReservationByBookAndFilteredByStatusList(any(Book.class), anyList())).willReturn(2);

        book.setNbCopy(2); // 2 reservations - ouvrage en 2 exemplaires - limite de réservation = 4
        Assertions.assertTrue(
                bookReservationService.computeIsReservationAvailable(book),
                "Réservation disponible : TRUE");

        book.setNbCopy(1); // 2 reservations - ouvrage en 1 exemplaire - limite de réservation = 2
        Assertions.assertFalse(
                bookReservationService.computeIsReservationAvailable(book),
                "Réservation disponible : FALSE");

    }
}
