package com.fp.tft.exception.handler;

import com.fp.tft.api.models.ServerError;
import com.fp.tft.exception.ErrorCodes;
import com.fp.tft.provider.match.MatchServiceException;
import com.fp.tft.provider.summoner.SummonerServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TFTExceptionHandlerTest {

    @InjectMocks
    private TFTExceptionHandler objectToTest;

    @Test
    void handleSummonerServiceException() {

        // Arrange
        var expectedResponseBody = ServerError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorCodes.TFT_SERVICE_ERROR.getResponseErrorCode())
                .build();

        // Act
        var response = objectToTest.handleSummonerServiceException(new SummonerServiceException("Error"));

        // Assert
        assertEquals(expectedResponseBody, response);
    }

    @Test
    void handleMatchServiceException() {

        // Arrange
        var expectedResponseBody = ServerError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorCodes.TFT_SERVICE_ERROR.getResponseErrorCode())
                .build();

        // Act
        var response = objectToTest.handleMatchServiceException(new MatchServiceException("Error"));

        // Assert
        assertEquals(expectedResponseBody, response);
    }
}
