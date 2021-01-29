package com.fp.tft.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String message) { super(message); }

    public ResourceNotFoundException(final String message, final Throwable cause) { super(message, cause); }
}
