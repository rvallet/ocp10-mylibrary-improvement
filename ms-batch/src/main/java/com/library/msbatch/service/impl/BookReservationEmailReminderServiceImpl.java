package com.library.msbatch.service.impl;

import com.library.msbatch.beans.BookReservationBean;
import com.library.msbatch.entities.BookReservationEmailReminder;
import com.library.msbatch.proxies.MicroServiceLibraryProxy;
import com.library.msbatch.repository.BookLoanEmailReminderRepository;
import com.library.msbatch.repository.BookReservationEmailReminderRepository;
import com.library.msbatch.service.BookReservationEmailReminderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookReservationEmailReminderServiceImpl implements BookReservationEmailReminderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookReservationEmailReminderServiceImpl.class);

    @Autowired
    private MicroServiceLibraryProxy msLibraryProxy;

    @Autowired
    BookReservationEmailReminderRepository bookReservationEmailReminderRepository;

    @Override
    public List<BookReservationBean> getBookReservationsList() {
        return null;
    }

    @Override
    public void feedBookReservationEmailReminderRepository(Long bookId) {
        List<BookReservationEmailReminder> result = new ArrayList<>();
        List<BookReservationBean> bookReservationList = msLibraryProxy.getBookReservationsList(bookId);
        if (!CollectionUtils.isEmpty(bookReservationList)) {
            for (BookReservationBean br : bookReservationList) {
                result.add(new BookReservationEmailReminder(
                        br.getUser().getId(),
                        br.getUser().getEmail(),
                        br.getUser().getLastName(),
                        br.getUser().getFirstName(),
                        br.getBook().getId(),
                        br.getBook().getTitle(),
                        br.getId(),
                        br.getCreationDate()
                ));
            }
            LOGGER.info("Ajout une liste de {} BookReservationEmailReminder", result.size());
            bookReservationEmailReminderRepository.saveAll(result);
        }
    }

    @Override
    public void saveBookReservationEmailReminderList(List<BookReservationEmailReminder> bookReservationEmailReminderList) {

    }

    @Override
    public List<BookReservationEmailReminder> findBookReservationEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent) {
        return null;
    }

    @Override
    public void saveBookReservationEmailReminder(BookReservationEmailReminder bookReservationEmailReminder) {

    }
}
