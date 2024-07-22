package com.gamza.eyedaero.core.error.exeption;


import com.gamza.eyedaero.core.error.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
