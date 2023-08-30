package com.ozeryavuzaslan.orderservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
@RequiredArgsConstructor
public class CustomStringBuilder {
    private final StringBuilder stringBuilder;

    public StringBuilder getDefaultExceptionMessages(MethodArgumentNotValidException exception){
        for (int i = 0; i < exception.getAllErrors().size(); i++){
            stringBuilder.append(exception.getAllErrors().get(i).getDefaultMessage());

            if (i != exception.getAllErrors().size() - 1)
                stringBuilder.append(" | ");
        }

        return stringBuilder;
    }
}
