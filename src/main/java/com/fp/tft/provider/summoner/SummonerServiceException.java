package com.fp.tft.provider.summoner;

public class SummonerServiceException extends RuntimeException {

    public SummonerServiceException(final String message) { super(message); }

    public SummonerServiceException(final String message, final Throwable cause) { super(message, cause); }
}
