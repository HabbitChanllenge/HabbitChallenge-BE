package org.example.saessakroutine.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "userAlreadyExists",
            "이미 가입된 이메일입니다."
    ),

    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "userNotFound",
            "사용자를 찾을 수 없습니다."
    ),

    PASSWORD_MISMATCH(
            HttpStatus.UNAUTHORIZED,
            "passwordMismatch",
            "비밀번호를 다시 확인해주세요."
    ),

    VALIDATION_ERROR(
            HttpStatus.BAD_REQUEST,
            "validationError",
            "입력값이 올바르지 않습니다."
    ),

    VERIFICATION_CODE_NOT_SENT(
            HttpStatus.BAD_REQUEST,
            "verificationCodeError",
            "인증번호를 먼저 발송해주세요."
    ),

    VERIFICATION_CODE_EXPIRED(
            HttpStatus.BAD_REQUEST,
            "verificationCodeError",
            "인증번호가 만료되었습니다."
    ),

    VERIFICATION_CODE_MISMATCH(
            HttpStatus.BAD_REQUEST,
            "verificationCodeError",
            "인증번호가 일치하지 않습니다."
    ),

    EMAIL_NOT_VERIFIED(
            HttpStatus.BAD_REQUEST,
            "verificationCodeError",
            "이메일 인증을 먼저 완료해주세요."
    );

    private final HttpStatus httpStatus;
    private final String type;
    private final String message;
}
