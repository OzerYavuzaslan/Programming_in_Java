package com.omniflow.common.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {
    private static final String STRING_NUMBER_VALUE = "100";
    private static final int EXPECTED_PARSED_INTEGER = 100;
    private static final String SUCCESS_PAYLOAD = "OmniFlow";
    private static final String ERROR_MESSAGE = "Not Enough Stock";

    @Test
    void shouldCreateSuccessResultAndReturnValue() {
        Result<String, String> result = Result.success(SUCCESS_PAYLOAD);
        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        String value = result.getOrThrow(RuntimeException::new);
        assertEquals(SUCCESS_PAYLOAD, value);
    }

    @Test
    void shouldCreateFailureResultAndThrowException() {
        Result<String, String> result = Result.failure(ERROR_MESSAGE);
        assertTrue(result.isFailure());
        assertFalse(result.isSuccess());
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> result.getOrThrow(IllegalStateException::new));
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldMapSuccessValueToAnotherType() {
        Result<String, String> result = Result.success(STRING_NUMBER_VALUE);
        Result<Integer, String> mappedResult = result.map(Integer::parseInt);
        assertTrue(mappedResult.isSuccess());
        assertEquals(EXPECTED_PARSED_INTEGER, mappedResult.getOrThrow(RuntimeException::new));
    }
}