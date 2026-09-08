package org.example.saessakroutine.user.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 60, message = "이름은 60자 이하여야 합니다.")
    String userId,

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 60)
    String email,

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 30, message = "비밀번호는 6자 이상 30자 이하여야 합니다.")
    @Pattern(
            regexp = "^(?=\\S{6,30}$)(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).*$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
    )
    String password
    ){
}
