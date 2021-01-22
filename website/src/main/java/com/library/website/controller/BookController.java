package com.library.website.controller;

import com.library.website.beans.BookBean;
import com.library.website.beans.BookLoanBean;
import com.library.website.beans.BookReservationBean;
import com.library.website.proxies.MicroServiceLibraryProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    MicroServiceLibraryProxy msLibraryProxy;

    @GetMapping("/livres")
    public String bookList (
            @RequestParam(name="searchCriteria", required = false) String searchCriteria,
            @RequestParam(name="searchValue", required = false) String searchValue,
            Model model) {

        LOGGER.debug("Envoie d'une demande de listes de livres");
        List<BookBean> bookList = msLibraryProxy.getBookList();
        List<String> searchCriteriaList = msLibraryProxy.getSearchCriteriaList();

        if (searchCriteria != null && searchValue != null) {
            LOGGER.debug("Recherche de livres de type {} contient '{}'", searchCriteria, searchValue);
            if (searchCriteriaList != null) {
                if (searchCriteria.trim().toLowerCase().equalsIgnoreCase(searchCriteriaList.get(0))) {
                    bookList = bookList.stream()
                            .filter(b -> b.getAuthor().toLowerCase().contains(searchValue.trim().toLowerCase()))
                            .collect(Collectors.toList());
                }
                if (searchCriteria.trim().toLowerCase().equalsIgnoreCase(searchCriteriaList.get(1))) {
                    bookList = bookList.stream()
                            .filter(b -> b.getTitle().toLowerCase().contains(searchValue.trim().toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (searchCriteria.trim().toLowerCase().equalsIgnoreCase(searchCriteriaList.get(2))) {
                    bookList = bookList.stream()
                            .filter(b -> b.getCollection().toLowerCase().contains(searchValue.trim().toLowerCase()))
                            .collect(Collectors.toList());
                }

                model.addAttribute("searchCriteria", searchCriteria);
                model.addAttribute("searchValue", searchValue);
            }
        }

        Map<Integer, Integer> nbBookReservationByBookId = msLibraryProxy.getNbCurrentBookListReservation(bookList);
        //TODO: /getNbCurrentBookListReservations

        Map<Integer, Integer> endloanDateByBookId = new HashMap<>();
        List<BookLoanBean> blList = msLibraryProxy.getBookLoansList();


        LOGGER.info("Réception d'une liste de {} livres.", bookList.size());
        model.addAttribute("bookList", bookList);
        model.addAttribute("nbBookReservationByBookId", nbBookReservationByBookId);
        model.addAttribute("endloanDateByBookId", endloanDateByBookId);
        model.addAttribute("searchCriteriaList", searchCriteriaList );
        return "livres";
    }

    @GetMapping("/livre")
    public String displayBook(
            Model model,
            @RequestParam(name = "id_book", required = false) Long bookId
    ) {
        if (bookId == null) {
            return "livres";
        }

        LOGGER.info("Envoi d'une demande de livre id_book={}", bookId);
        BookBean book = msLibraryProxy.getBookById(bookId);
        if (book == null) {
            return "livres";
        }

        model.addAttribute("book", book);
        return "livre";
    }
}
