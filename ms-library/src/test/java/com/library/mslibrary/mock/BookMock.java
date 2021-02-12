package com.library.mslibrary.mock;

import com.library.mslibrary.entities.Book;

import java.util.Date;

public class BookMock {

    private BookMock(){};

    public static Book getMockBook(){
        return new Book(
                "title",
                "description",
                "author",
                "editor",
                "collection",
                "isbn",
                new Date()
        );
    }
}
