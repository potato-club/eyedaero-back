package com.gamza.eyedaero.core.error.exeption;

import com.gamza.eyedaero.core.error.ErrorCode;

public class InvalidTokenException extends BusinessException{

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
