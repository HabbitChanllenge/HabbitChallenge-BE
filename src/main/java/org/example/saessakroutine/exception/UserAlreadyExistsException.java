package org.example.saessakroutine.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("이미 가입된 이메일입니다.");
    }
}
