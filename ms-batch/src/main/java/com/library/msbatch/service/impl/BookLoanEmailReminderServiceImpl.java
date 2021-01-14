package com.library.msbatch.service.impl;

import com.library.msbatch.beans.BookLoanBean;
import com.library.msbatch.entities.BookLoanEmailReminder;
import com.library.msbatch.proxies.MicroServiceLibraryProxy;
import com.library.msbatch.repository.BookLoanEmailReminderRepository;
import com.library.msbatch.service.BookLoanEmailReminderService;
import com.library.msbatch.utils.DateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookLoanEmailReminderServiceImpl implements BookLoanEmailReminderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookLoanEmailReminderServiceImpl.class);

    @Autowired
    private MicroServiceLibraryProxy msLibraryProxy;

    @Autowired
    BookLoanEmailReminderRepository bookLoanEmailReminderRepository;

    @Override
    public List<BookLoanBean> getBookLoansList() {
        LOGGER.info("Récupération de la liste des emprunts de livres");
        return msLibraryProxy.getBookLoansList();
    }

    @Override
    public void feedBookLoanEmailReminderRepository() {
        List<BookLoanEmailReminder> result = new ArrayList<>();

        List<BookLoanBean> bookLoanBeanList = msLibraryProxy.getBookLoansList();
        if (bookLoanBeanList !=null && !bookLoanBeanList.isEmpty()) {
            LOGGER.info("Get bookLoanList : {}", bookLoanBeanList.size());

            // Conserve uniquement les emprunts dont l'échéance est la veille
            List<BookLoanBean> yesterdayBookLoanList = bookLoanBeanList.stream()
                    .filter(b -> b.getEndLoan().after(DateTools.yesterdayTheDayBefore()) && b.getEndLoan().before(new Date()))
                    .filter(b -> !b.getLoanStatus().equalsIgnoreCase("Terminé"))
                    .collect(Collectors.toList());

            LOGGER.info("Filter bookLoanList : {}", yesterdayBookLoanList.size());

            for (BookLoanBean bookLoan : yesterdayBookLoanList) {
                result.add(new BookLoanEmailReminder(
                        bookLoan.getUser().getId(),
                        bookLoan.getUser().getEmail(),
                        bookLoan.getUser().getLastName(),
                        bookLoan.getUser().getFirstName(),
                        bookLoan.getBook().getId(),
                        bookLoan.getBook().getTitle(),
                        bookLoan.getId(),
                        bookLoan.getEndLoan()
                ));
            }
            saveBookLoanEmailReminderList(result);
        }
    }

    @Override
    public void saveBookLoanEmailReminderList(List<BookLoanEmailReminder> bookLoanEmailReminderList) {
        bookLoanEmailReminderRepository.saveAll(bookLoanEmailReminderList);
    }

    @Override
    public List<BookLoanEmailReminder> findBookLoanEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent) {
        return bookLoanEmailReminderRepository.findBookLoanEmailRemindersByIsEmailSentIsNot(isEmailSent);
    }

    @Override
    public void saveBookLoanEmailReminder(BookLoanEmailReminder bookLoanEmailReminder) {
        bookLoanEmailReminderRepository.save(bookLoanEmailReminder);
    }

}
