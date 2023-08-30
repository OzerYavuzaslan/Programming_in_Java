package com.ozeryavuzaslan.basedomains.util;

import org.springframework.web.bind.MethodArgumentNotValidException;

public interface CustomStringBuilder{
        StringBuilder getDefaultExceptionMessages(MethodArgumentNotValidException exception);
}
