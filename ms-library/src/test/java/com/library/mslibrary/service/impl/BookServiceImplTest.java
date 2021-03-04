package com.library.mslibrary.service.impl;

import com.library.mslibrary.entities.Book;
import com.library.mslibrary.enumerated.SearchCriteriaEnum;
import com.library.mslibrary.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import static com.library.mslibrary.mock.BookMock.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void findAll(){
        List<Book> bookList = getMockBookList();

        given(bookRepository.findAll(any(Sort.class))).willReturn(bookList);

        Assertions.assertEquals(
                bookList.size(),
                bookService.findAll().size(),
                "Liste des livres"
        );
    }

    @Test
    void findBookById(){
        Book book = getMockBook();
        long idBook = 1;
        book.setId(idBook);
        given(bookRepository.findBookById(anyLong())).willReturn(book);

        Assertions.assertEquals(
                idBook,
                bookService.findBookById(idBook).getId(),
                "Recherche de livre par bookId"
        );
    }

    @Test
    void findBookByIsbn(){
        Book book = getMockBook();
        String isbn = "isbn";
        book.setIsbn(isbn);
        given(bookRepository.findBookByIsbn(anyString())).willReturn(book);

        Assertions.assertEquals(
                isbn,
                bookService.findBookByIsbn(isbn).getIsbn(),
                "Recherche de livre par ISBN"
        );
    }

    @Test
    void saveBook(){
        Book book = getMockBook();

        given(bookRepository.save(any(Book.class))).willReturn(book);

        Assertions.assertEquals(
                book.getId(),
                bookService.saveBook(book).getId(),
                "Enregistrement d'un livre"
        );
    }

    @Test
    void saveAll(){
        List<Book> bookList = getMockBookList();

        given(bookRepository.saveAll(anyIterable())).willReturn(bookList);

        Assertions.assertEquals(
                bookList.size(),
                bookService.saveAll(bookList).size(),
                "Enregistrement d'une liste de livres"
        );
    }

    @Test
    void findBooksWithCriteria(){
        String searchAuthor = "auteur";
        String searchTitle = "titre";

        Book book1 = getMockBook();
        Book book2 = getMockBook();

        book1.setAuthor(searchAuthor);
        book2.setTitle(searchTitle);

        String authorCriteria = SearchCriteriaEnum.BY_AUTHOR.toString();
        String titleCriteria = SearchCriteriaEnum.BY_TITLE.toString();


        given(bookRepository.findBookByAuthor(anyString())).willReturn(Arrays.asList(book1));
        given(bookRepository.findBookByTitle(anyString())).willReturn(Arrays.asList(book2));

        Assertions.assertTrue(
                bookService.findBooksWithCriteria(authorCriteria, searchAuthor).stream().allMatch(o -> o.getAuthor().equals(searchAuthor)),
                "Recherche par Auteur"
        );

        Assertions.assertTrue(
                bookService.findBooksWithCriteria(titleCriteria, searchTitle).stream().allMatch(o -> o.getAuthor().equals(searchTitle)),
                "Recherche par Titre"
        );

    }

    @Test
    void getSearchCriteriaList(){
        Assertions.assertEquals(
                SearchCriteriaEnum.values().length,
                bookService.getSearchCriteriaList().size(),
                ""
        );
    }
}
