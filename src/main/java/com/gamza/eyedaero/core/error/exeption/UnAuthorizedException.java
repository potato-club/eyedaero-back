package com.gamza.eyedaero.core.error.exeption;

import com.gamza.eyedaero.core.error.ErrorCode;
import lombok.Getter;

@Getter
public class UnAuthorizedException extends BusinessException {

    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message,errorCode);
    }
}
