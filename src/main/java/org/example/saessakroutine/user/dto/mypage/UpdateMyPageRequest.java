package org.example.saessakroutine.user.dto.mypage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMyPageRequest (

        @Size(max = 60)
        String userId,

        @Email
        @Size(max = 60)
        String email,

        String currentPassword,

        @Size(min = 6, max = 30, message = "비밀번호는 6자 이상 30자 이하여야 합니다.")
        @Pattern(
                regexp = "^(?=\\S{6,30}$)(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).*$",
                message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        String newPassword
){
}
