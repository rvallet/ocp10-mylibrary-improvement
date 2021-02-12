package com.library.mslibrary.service.impl;

import com.library.mslibrary.config.ApplicationPropertiesConfig;
import com.library.mslibrary.entities.BookLoan;
import com.library.mslibrary.enumerated.BookLoanStatusEnum;
import com.library.mslibrary.enumerated.BookReservationStatusEnum;
import com.library.mslibrary.repository.BookLoanRepository;
import com.library.mslibrary.repository.BookReservationRepository;
import com.library.mslibrary.service.BookLoanService;
import com.library.mslibrary.utils.DateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class BookLoanServiceImpl implements BookLoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookLoanServiceImpl.class);

    @Autowired
    BookLoanRepository bookLoanRepository;

    @Autowired
    private ApplicationPropertiesConfig appConfig;

    @Override
    public List<BookLoan> findAll() {
        return bookLoanRepository.findAll();
    }

    @Override
    public BookLoan findBookLoanById(Long id) {
        return bookLoanRepository.findBookLoanById(id);
    }

    @Override
    public List<BookLoan> findBookLoansByUserId (Long userId) {
        List<BookLoan> bookLoanList = bookLoanRepository.findBookLoansByUserId(userId);
        LOGGER.info("Envoie d'une liste de {} emprunts (utilisateur id = {}).", bookLoanList.size(), userId);
        return bookLoanList;
    }

    @Override
    public BookLoan saveBookLoan(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }

    @Override
    public BookLoan extendBookLoan(Long bookLoanId) {
        BookLoan bl = bookLoanRepository.findBookLoanById(bookLoanId);
        if (isExtendable(bl)) {
            bl.setLoanExtended(true);
            bl.setLoanStatus(BookLoanStatusEnum.EXTENDED.toString());
            bl.setEndLoan(DateTools.addDays(bl.getEndLoan(), appConfig.getBookLoanDuration()));
            LOGGER.info(
                    "Prolongation de l'emprunt id {} (Status {} - Date de fin : {})",
                    bookLoanId,
                    bl.getLoanStatus(),
                    bl.getEndLoan());
            bookLoanRepository.save(bl);
        } else {
            // Bug issue #2 (BookLoan could not be extend after endLoan Date)
            LOGGER.debug(
                    "L'emprunt id {} ne peut être prolongé après échéance (Status {} - Date de fin : {})",
                    bookLoanId,
                    bl.getLoanStatus(),
                    bl.getEndLoan());
        }
        return bl;
    }

    private Boolean isExtendable(BookLoan bl){
        return bl.getEndLoan().after(new Date());
    }

    @Override
    public BookLoan closeBookLoan(Long bookLoanId) {
        BookLoan bl = bookLoanRepository.findBookLoanById(bookLoanId);
        bl.setReturnLoan(new Date());
        bl.getBook().setStock(bl.getBook().getStock() +1);
        bl.getBook().setLoanAvailable(true);
        bl.setLoanStatus(BookLoanStatusEnum.CLOSED.toString());
        bl.getBook().setReservationAvailable(true);
        LOGGER.info("Clôture de l'emprunt id {} (Status {} - Date de retour : {})", bookLoanId, bl.getLoanStatus(), bl.getReturnLoan());
        return bookLoanRepository.save(bl);
    }

    @Override
    public List<BookLoan> saveAll(List<BookLoan> bookLoanList) {
        return bookLoanRepository.saveAll(bookLoanList);
    }

    @Override
    public List<BookLoan> findBookLoansByBookId (Long bookId) {
        return bookLoanRepository.findBookLoansByBookId(bookId);
    }

    public String getNextBookloanEndDate(Long bookId){
        List<BookLoan> blList = bookLoanRepository.findBookLoansByBookIdOrderByEndLoan(bookId);
        String result = "";
        if (!CollectionUtils.isEmpty(blList)) {
            result = DateTools.dateFormat(blList.get(0).getEndLoan());
        }
     return result;
    }

}
