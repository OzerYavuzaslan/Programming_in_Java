package com.ozeryavuzaslan.basedomains.util.enums;

import lombok.Getter;

@Getter
public enum KnownExceptionHTTPStatusCodes {
    CONFLICT(409),
    NOT_FOUND(404),
    UNPROCESSABLE_ENTITY(422),
    NOT_MODIFIED(304),
    NOT_ACCEPTABLE(406),
    BAD_REQUEST(400);

    private final int httpStatusCode;

    KnownExceptionHTTPStatusCodes(int httpStatusCode){
        this.httpStatusCode = httpStatusCode;
    }
}
