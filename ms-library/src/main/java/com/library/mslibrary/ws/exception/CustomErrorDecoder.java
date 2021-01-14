package com.library.mslibrary.ws.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String request, Response response) {

        if(response.status() == 400 ) {
            return new NoSuchResultException("Requête incorrecte");
        }

        return defaultErrorDecoder.decode(request, response);
    }

}
