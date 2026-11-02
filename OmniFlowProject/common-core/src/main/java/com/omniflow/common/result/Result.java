package com.omniflow.common.result;

import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Success, Result.Failure {
    record Success<T, E>(T value) implements Result<T, E> {}
    record Failure<T, E>(E error) implements Result<T, E> {}

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    default T getOrThrow(Function<E, ? extends RuntimeException> exceptionSupplier) {
        if (this instanceof Result.Success<T, E> success) {
            return success.value();
        } else if (this instanceof Result.Failure<T, E> failure) {
            throw exceptionSupplier.apply(failure.error());
        } else {
            throw new IllegalStateException("Unexpected state");
        }
    }

    default <R> Result<R, E> map(Function<T, R> mapper) {
        if (this instanceof Result.Success<T, E> success) {
            return Result.success(mapper.apply(success.value()));
        } else if (this instanceof Result.Failure<T, E> failure) {
            return Result.failure(failure.error());
        } else {
            throw new IllegalStateException("Unexpected state");
        }
    }
}