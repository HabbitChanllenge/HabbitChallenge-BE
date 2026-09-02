package org.example.saessakroutine.user.dto.mypage;


public record MyPageResponse(
        Long userId,
        String name,
        String email,
        int statusCode
) {
}
