package com.library.mslibrary.service;

import com.library.mslibrary.entities.Book;


import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findBookById (Long id);
    Book findBookByIsbn (String isbn);
    Book saveBook (Book book);
    List<Book> saveAll (List<Book> bookList);
    List<Book> findBooksWithCriteria(String searchCriteria, String searchValue);
    List<String> getSearchCriteriaList ();
}
