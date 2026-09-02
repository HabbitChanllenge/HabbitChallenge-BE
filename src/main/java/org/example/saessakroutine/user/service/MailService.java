package org.example.saessakroutine.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("[새싹루틴] 비밀번호 재설정 인증번호");
        message.setText("""
                안녕하세요. 새싹루틴입니다.
                
                비밀번호 재설정 인증번호는 %s 입니다.
                인증번호는 5분 동안 유효합니다.
                """.formatted(code));

        mailSender.send(message);
    }
}
