package org.example.saessakroutine.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateMyPageRequest (

        @Size(max = 60)
        String userId,

        @Email
        @Size(max = 60)
        String email,

        String currentPassword,

        @Size(min = 8, max = 30)
        String newPassword
){
}
