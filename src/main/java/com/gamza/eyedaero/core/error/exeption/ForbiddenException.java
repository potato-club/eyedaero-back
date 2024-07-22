package com.gamza.eyedaero.core.error.exeption;


import com.gamza.eyedaero.core.error.ErrorCode;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message, ErrorCode errorCode)
    {
        super(message, errorCode);
    }
}
