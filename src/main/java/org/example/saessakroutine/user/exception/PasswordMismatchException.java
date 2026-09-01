package org.example.saessakroutine.user.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super("비밀번호를 다시 확인해주세요.");
    }
}
