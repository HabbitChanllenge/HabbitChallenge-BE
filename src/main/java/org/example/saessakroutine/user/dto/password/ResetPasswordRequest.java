package org.example.saessakroutine.user.dto.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식을 입력해주세요.")
        String email,

        @NotBlank(message = "새 비밀번호는 필수입니다.")
        @Size(
                min = 8,
                max = 30,
                message = "비밀번호는 8자리 이상 30자리 이하여야 합니다."
        )
        String newPassword
) {
}
