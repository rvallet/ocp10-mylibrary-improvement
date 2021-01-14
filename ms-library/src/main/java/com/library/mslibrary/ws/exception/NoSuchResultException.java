package com.library.mslibrary.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResultException extends RuntimeException {

    public NoSuchResultException(String message) {
        super(message);
    }

}
