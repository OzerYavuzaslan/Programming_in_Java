package com.ozeryavuzaslan.basedomains.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TypeFactoryHelper {
    public static <T> Type constructCollectionType(Class<T> clazz) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{
                        clazz
                };
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static <T> JavaType constructCollectionType(Class<T> clazz, ObjectMapper objectMapper) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
    }
}