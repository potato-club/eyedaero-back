package com.gamza.eyedaero.core.error.exeption;


import com.gamza.eyedaero.core.error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
