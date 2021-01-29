package com.fp.tft.exception.handler;

import com.fp.tft.api.models.ServerError;
import com.fp.tft.exception.ErrorCodes;
import com.fp.tft.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception Handler for Not Found Exceptions
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class NotFoundExceptionHandler {

    @ExceptionHandler({ ResourceNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ServerError handleResourceNotFoundException(final ResourceNotFoundException e) {
        log.error("Handling SummonerServiceException: ", e);
        return ServerError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ErrorCodes.NOT_FOUND.getResponseErrorCode())
                .build();
    }
}
