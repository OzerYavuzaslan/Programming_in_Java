package com.omniflow.common.result;

import java.util.function.Function;

/**
 * Represents the outcome of an operation that can either succeed with a value of type {@code T}
 * or fail with an error of type {@code E}.
 * <p>
 * This interface implements the <i>Railway-Oriented Programming</i> (Either pattern) to avoid
 * expensive Java runtime exception allocations in high-throughput distributed systems.
 *
 * @param <T> the type of the successful value
 * @param <E> the type of the failure error
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
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