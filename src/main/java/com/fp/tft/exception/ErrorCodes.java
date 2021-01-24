package com.fp.tft.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCodes {

    GENERIC("TFT-000"),

    BAD_REQUEST("TFT-001"),

    NOT_FOUND("TFT-002"),

    TFT_SERVICE_ERROR("TFT-003");

    @Getter
    private final String responseErrorCode;
}
