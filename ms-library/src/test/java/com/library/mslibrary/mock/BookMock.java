package com.library.mslibrary.mock;

import com.library.mslibrary.entities.Book;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BookMock {

    private BookMock(){};

    public static Book getMockBook(){
        Book book = new Book(
                "title",
                "description",
                "author",
                "editor",
                "collection",
                "isbn",
                new Date()
        );
        book.setId(0L);
        return book;
    }

    public static List<Book> getMockBookList(){
        return Arrays.asList(getMockBook(), getMockBook());
    }


}
