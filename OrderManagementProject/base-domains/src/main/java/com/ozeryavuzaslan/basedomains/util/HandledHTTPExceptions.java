package com.ozeryavuzaslan.basedomains.util;

import com.ozeryavuzaslan.basedomains.util.enums.KnownExceptionHTTPStatusCodes;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HandledHTTPExceptions {
    private final static HashSet<Integer> knownExceptionHTTPCodes = new HashSet<>();

    static {
        knownExceptionHTTPCodes.add(KnownExceptionHTTPStatusCodes.CONFLICT.getHttpStatusCode());
        knownExceptionHTTPCodes.add(KnownExceptionHTTPStatusCodes.BAD_REQUEST.getHttpStatusCode());
        knownExceptionHTTPCodes.add(KnownExceptionHTTPStatusCodes.NOT_MODIFIED.getHttpStatusCode());
        knownExceptionHTTPCodes.add(KnownExceptionHTTPStatusCodes.NOT_FOUND.getHttpStatusCode());
        knownExceptionHTTPCodes.add(KnownExceptionHTTPStatusCodes.UNPROCESSABLE_ENTITY.getHttpStatusCode());
        knownExceptionHTTPCodes.add(KnownExceptionHTTPStatusCodes.NOT_ACCEPTABLE.getHttpStatusCode());
        knownExceptionHTTPCodes.add(KnownExceptionHTTPStatusCodes.SERVICE_UNAVAILABLE.getHttpStatusCode());
    }

    public static boolean checkHandledExceptionStatusCode(int statusCode) {
        return knownExceptionHTTPCodes.contains(statusCode) || areNotKnownExceptionStatusAndHttpOKCodes(statusCode);
    }

    public static HttpStatus getProperHTTPStatus(int statusCode) {
        switch (statusCode) {
            case 304 -> {
                return HttpStatus.NOT_MODIFIED;
            }
            case 404 -> {
                return HttpStatus.NOT_FOUND;
            }
            case 409 -> {
                return HttpStatus.CONFLICT;
            }
            case 422 -> {
                return HttpStatus.UNPROCESSABLE_ENTITY;
            }
            case 406 -> {
                return HttpStatus.NOT_ACCEPTABLE;
            }
            case 400 -> {
                return HttpStatus.BAD_REQUEST;
            }
            case 503 -> {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }
            default -> {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    private static boolean areNotKnownExceptionStatusAndHttpOKCodes(int statusCode){
        return statusCode != HttpStatus.OK.value() && !knownExceptionHTTPCodes.contains(statusCode);
    }
}