package com.gamza.eyedaero.core.error.exeption;

import com.gamza.eyedaero.core.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends BusinessException{

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
