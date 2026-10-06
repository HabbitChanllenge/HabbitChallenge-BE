package org.example.saessakroutine.user.exception.password;

import org.example.saessakroutine.global.exception.BusinessException;
import org.example.saessakroutine.global.exception.ErrorCode;

public class VerificationCodeException extends BusinessException {

    public VerificationCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
