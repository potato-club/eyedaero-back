package com.gamza.eyedaero.core.error.exeption;

import com.gamza.eyedaero.core.error.ErrorCode;
import lombok.Getter;

@Getter
public class JwtException extends BusinessException{
    public JwtException(String message, ErrorCode errorCode){
        super(message,errorCode);
    }
}
