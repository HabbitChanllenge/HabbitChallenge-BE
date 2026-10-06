package org.example.saessakroutine.user.exception;

import org.example.saessakroutine.global.exception.BusinessException;
import org.example.saessakroutine.global.exception.ErrorCode;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
