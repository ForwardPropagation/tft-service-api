package com.fp.tft.exception.handler;

import com.fp.tft.api.models.ServerError;
import com.fp.tft.exception.ErrorCodes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GenericExceptionHandlerTest {

    @InjectMocks
    private GenericExceptionHandler objectToTest;

    @Test
    void handleException() {

        // Arrange
        var expectedResponseBody = ServerError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorCodes.GENERIC.getResponseErrorCode())
                .build();

        // Act
        var response = objectToTest.handleException(new RuntimeException("Error"));

        // Assert
        assertEquals(expectedResponseBody, response);
    }
}
