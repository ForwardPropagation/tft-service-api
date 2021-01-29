package com.fp.tft.exception.handler;

import com.fp.tft.api.models.ServerError;
import com.fp.tft.exception.ErrorCodes;
import com.fp.tft.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NotFoundExceptionHandlerTest {

    @InjectMocks
    private NotFoundExceptionHandler objectToTest;

    @Test
    void handleResourceNotFoundException() {

        // Arrange
        final ServerError expectedResponseBody = ServerError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ErrorCodes.NOT_FOUND.getResponseErrorCode())
                .build();

        // Act
        final ServerError response = objectToTest.handleResourceNotFoundException(new ResourceNotFoundException("Error"));

        // Assert
        assertEquals(expectedResponseBody, response);
    }
}
