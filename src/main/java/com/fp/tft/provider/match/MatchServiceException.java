package com.fp.tft.provider.match;

public class MatchServiceException extends RuntimeException {

    public MatchServiceException(final String message) { super(message); }

    public MatchServiceException(final String message, final Throwable cause) { super(message, cause); }
}
