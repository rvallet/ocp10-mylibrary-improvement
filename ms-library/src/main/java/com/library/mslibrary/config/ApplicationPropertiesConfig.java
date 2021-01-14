package com.library.mslibrary.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("config-ms-library")
public class ApplicationPropertiesConfig {

    private int pageSizeLimit;

    private int bookLoanDuration;

    public int getPageSizeLimit() {
        return pageSizeLimit;
    }

    public void setPageSizeLimit(int pageSizeLimit) {
        this.pageSizeLimit = pageSizeLimit;
    }

    public int getBookLoanDuration() { return bookLoanDuration; }

    public void setBookLoanDuration(int bookLoanDuration) {this.bookLoanDuration = bookLoanDuration;}

}
