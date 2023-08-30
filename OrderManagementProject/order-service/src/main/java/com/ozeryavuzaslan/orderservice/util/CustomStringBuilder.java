package com.ozeryavuzaslan.orderservice.util;

import org.springframework.web.bind.MethodArgumentNotValidException;

public interface CustomStringBuilder{
        StringBuilder getDefaultExceptionMessages(MethodArgumentNotValidException exception);
}
