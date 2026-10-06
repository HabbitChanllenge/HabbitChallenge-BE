package org.example.saessakroutine.user.exception;

import org.example.saessakroutine.global.exception.BusinessException;
import org.example.saessakroutine.global.exception.ErrorCode;

public class PasswordMismatchException extends BusinessException {

    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
