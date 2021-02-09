package com.library.msbatch.service.impl;

import com.library.msbatch.beans.BookReservationBean;
import com.library.msbatch.config.ApplicationPropertiesConfig;
import com.library.msbatch.entities.BookReservationEmailReminder;
import com.library.msbatch.proxies.MicroServiceLibraryProxy;
import com.library.msbatch.repository.BookReservationEmailReminderRepository;
import com.library.msbatch.service.BookReservationEmailReminderService;
import com.library.msbatch.utils.DateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookReservationEmailReminderServiceImpl implements BookReservationEmailReminderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookReservationEmailReminderServiceImpl.class);

    @Autowired
    MicroServiceLibraryProxy msLibraryProxy;

    @Autowired
    BookReservationEmailReminderRepository bookReservationEmailReminderRepository;

    @Autowired
    ApplicationPropertiesConfig applicationPropertiesConfig;

    @Override
    public void feedBookReservationEmailReminderRepository(Long bookId) {
        List<BookReservationBean> bookReservationList = msLibraryProxy.getBookReservationsList(bookId);
        if (!CollectionUtils.isEmpty(bookReservationList)) {
            Optional<BookReservationBean> br = bookReservationList
                    .stream()
                    .filter(o -> !checkBookReservationEmailReminderAlreadyExists(o))
                    .findFirst();
            if (br.isPresent()) {
                BookReservationEmailReminder result = new BookReservationEmailReminder(
                        br.get().getUser().getId(),
                        br.get().getUser().getEmail(),
                        br.get().getUser().getLastName(),
                        br.get().getUser().getFirstName(),
                        br.get().getBook().getId(),
                        br.get().getBook().getTitle(),
                        br.get().getId(),
                        br.get().getCreationDate()
                );
                LOGGER.info("Ajout d'un BookReservationEmailReminder : {}", result);
                bookReservationEmailReminderRepository.save(result);
            }
            LOGGER.info("Aucun ajout de BookReservationEmailReminder nécessaire parmis les {}", bookReservationList.size());
        }
    }

    private boolean checkBookReservationEmailReminderAlreadyExists(BookReservationBean bookReservation) {
        boolean result = false;
        List<BookReservationEmailReminder> bookReservationEmailReminderList =
                bookReservationEmailReminderRepository
                .findBookReservationEmailRemindersByBookIdAndAndUserIdOrderByBookReservationIdDesc(
                    bookReservation.getBook().getId(),
                    bookReservation.getUser().getId()
                );

        if (!CollectionUtils.isEmpty(bookReservationEmailReminderList)) {
            result = bookReservationEmailReminderList
                    .stream()
                    .filter(e -> e.getSendingEmailDate().after(DateTools.addDays(new Date(), -60)))
                    .findFirst()
                    .isPresent();
        }

        return result;
    }

    @Override
    public void closeBookReservationAfterDeadline(){
        bookReservationEmailReminderRepository.findBookReservationEmailRemindersByIsEmailSent(Boolean.TRUE)
                .stream()
                .filter(e -> isReservationNotificationDeadlineReached(e))
                .forEach(e -> msLibraryProxy.changeBookReservationStatusToExpired(e.getBookReservationId()));
    }

    @Override
    public List<BookReservationEmailReminder> findBookReservationEmailRemindersByIsEmailSentIsNot(Boolean isEmailSent) {
        return bookReservationEmailReminderRepository.findBookReservationEmailRemindersByIsEmailSentIsNot(isEmailSent);
    }

    private boolean isReservationNotificationDeadlineReached(BookReservationEmailReminder bookReservationEmailReminder){
        return bookReservationEmailReminder.getSendingEmailDate().before(DateTools.addDays(new Date(), - applicationPropertiesConfig.getBookReservationDeadline()));
    }

}
