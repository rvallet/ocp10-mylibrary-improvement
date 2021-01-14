package com.library.mslibrary.repository;

import com.library.mslibrary.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findAll();

    @Query("SELECT book from Book book WHERE book.id = :id")
    Book findBookById(Long id);

    Book findBookByIsbn(String isbn);

    @Query("SELECT books from Book books WHERE books.title LIKE CONCAT ('%',:bookTitle,'%')")
    List<Book> findBookByTitle(String bookTitle);

    @Query("SELECT books from Book books WHERE books.author LIKE CONCAT ('%',:bookAuthor,'%')")
    List<Book> findBookByAuthor(String bookAuthor);


}
