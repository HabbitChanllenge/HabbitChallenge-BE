package org.example.saessakroutine.user.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.user.dto.password.ResetPasswordRequest;
import org.example.saessakroutine.user.dto.password.SendVerificationCodeRequest;
import org.example.saessakroutine.user.dto.password.VerifyCodeRequest;
import org.example.saessakroutine.user.entity.PasswordResetCode;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.exception.UserNotFoundException;
import org.example.saessakroutine.user.exception.password.VerificationCodeException;
import org.example.saessakroutine.user.repository.PasswordResetCodeRepository;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService {

    private static final int CODE_EXPIRATION_MINUTES = 5;

    private final UserRepository userRepository;
    private final PasswordResetCodeRepository passwordResetCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private final SecureRandom secureRandom = new SecureRandom();

    public void sendVerificationCode(SendVerificationCodeRequest request) {
        if (!userRepository.existsByEmail(request.email())) {
            throw new UserNotFoundException();
        }

        String code = createVerificationCode();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES);

        passwordResetCodeRepository.findByEmail(request.email())
                .ifPresentOrElse(passwordResetCode -> passwordResetCode.updateCode(code, expiresAt),
                        () -> passwordResetCodeRepository.save(
                                PasswordResetCode.builder()
                                        .email(request.email())
                                        .code(code)
                                        .expiresAt(expiresAt)
                                        .build()
                        ));
        mailService.sendVerificationCode(request.email(),  code);
    }

    public void verifyCode(VerifyCodeRequest request) {
        PasswordResetCode passwordResetCode =
                passwordResetCodeRepository.findByEmail(request.email())
                        .orElseThrow(() -> new VerificationCodeException("인증번호를 먼저 발송해주세요."));

        if (passwordResetCode.isExpired()) {
            throw new VerificationCodeException("인증번호가 만료되었습니다.");
        }

        if (!passwordResetCode.getCode().equals(request.code())) {
            throw new VerificationCodeException("인증번호가 일치하지 않습니다.");
        }

        passwordResetCode.markVerified();
    }

    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        PasswordResetCode passwordResetCode = passwordResetCodeRepository.findByEmail(request.email())
                .orElseThrow(() -> new VerificationCodeException("이메일 인증을 먼저 완료해주세요."));

        if (passwordResetCode.isExpired()) {
            throw new VerificationCodeException("인증번호가 만료되었습니다.");
        }

        if (!passwordResetCode.isVerified()) {
            throw new VerificationCodeException("이메일 인증을 먼저 완료해주세요.");
        }

        user.updatePassword(passwordEncoder.encode(request.newPassword()));

        passwordResetCodeRepository.delete(passwordResetCode);
    }

    private String createVerificationCode() {
        return String.valueOf(secureRandom.nextInt(900000) + 100000);
    }
}
