package com.ozeryavuzaslan.basedomains.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TypeFactoryHelper {
    public static <T> JavaType constructCollectionType(Class<T> clazz, ObjectMapper objectMapper) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
    }
}