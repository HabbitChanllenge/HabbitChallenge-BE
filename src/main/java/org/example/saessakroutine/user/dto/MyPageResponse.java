package org.example.saessakroutine.user.dto;


public record MyPageResponse(
        Long userId,
        String name,
        String email,
        int statusCode
) {
}
