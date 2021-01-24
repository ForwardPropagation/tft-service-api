package com.fp.tft.exception.handler;

import com.fp.tft.api.models.ServerError;
import com.fp.tft.exception.ErrorCodes;
import com.fp.tft.provider.summoner.SummonerServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception Handler for Downstream Service Exceptions
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TFTExceptionHandler {

    @ExceptionHandler({ SummonerServiceException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ServerError handleSummonerServiceException(final SummonerServiceException e) {
        log.error("Handling SummonerServiceException: ", e);
        return ServerError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorCodes.TFT_SERVICE_ERROR.getResponseErrorCode())
                .build();
    }
}
