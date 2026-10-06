package org.example.saessakroutine.user.exception;

import org.example.saessakroutine.global.exception.BusinessException;
import org.example.saessakroutine.global.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
