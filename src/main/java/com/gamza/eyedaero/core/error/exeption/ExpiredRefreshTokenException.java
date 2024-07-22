package com.gamza.eyedaero.core.error.exeption;


import com.gamza.eyedaero.core.error.ErrorCode;

public class ExpiredRefreshTokenException extends BusinessException {

    public ExpiredRefreshTokenException(String message, ErrorCode code) {
        super(message, code);
    }
}