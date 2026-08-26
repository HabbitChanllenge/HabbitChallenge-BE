package org.example.saessakroutine.dto;


public record MyPageResponse(
        Long userId,
        String name,
        String email,
        int statusCode
) {
}
