package org.example.saessakroutine.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.user.dto.password.ResetPasswordRequest;
import org.example.saessakroutine.user.dto.password.SendVerificationCodeRequest;
import org.example.saessakroutine.user.dto.password.VerifyCodeRequest;
import org.example.saessakroutine.user.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/email")
    public ResponseEntity<Map<String, Object>> sendVerificationCode(@Valid @RequestBody SendVerificationCodeRequest request) {
        passwordResetService.sendVerificationCode(request);

        return ResponseEntity.ok(
                Map.of(
                       "message", "인증번호가 이메일로 전송되었습니다.",
                        "status", 200
                ));
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyCode(@Valid @RequestBody VerifyCodeRequest request) {
        passwordResetService.verifyCode(request);

        return ResponseEntity.ok(
                Map.of(
                        "message", "이메일 인증이 완료되었습니다.",
                        "status", 200
                ));
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request);

        return ResponseEntity.ok(
                Map.of(
                        "message", "비밀번호가 성공적으로 변경되었습니다.",
                        "status", 200
                ));
    }
}
