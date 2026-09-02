package org.example.saessakroutine.user.dto.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyCodeRequest(

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식을 입력해주세요.")
        String email,

        @NotBlank(message = "인증번호는 필수입니다.")
        @Pattern(
                regexp = "\\d{6}",
                message = "인증번호는 숫자 6자리여야 합니다."
        )
        String code
) {
}
