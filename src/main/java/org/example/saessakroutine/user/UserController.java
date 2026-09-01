package org.example.saessakroutine.user;

import jakarta.validation.Valid;
import org.example.saessakroutine.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody SignupRequest request) {
        userService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "type", "userCreated",
                        "statusCode", 201
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        String accessToken = userService.login(request);

        return ResponseEntity
                .ok(Map.of(
                        "accessToken", accessToken,
                        "tokenType", "Bearer",
                        "statusCode", 200
                ));
    }

    @GetMapping("/user/me")
    public ResponseEntity<MyPageResponse> getMyPage(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(userService.getMyPage(email));
    }

    @PatchMapping("/user/me")
    public ResponseEntity<Map<String, Object>> updateMyPage(@AuthenticationPrincipal String email, @Valid @RequestBody UpdateMyPageRequest request) {
        String accessToken = userService.updateMyPage(email, request);

        return ResponseEntity
                .ok(Map.of(
                        "message", "마이페이지가 성공적으로 수정되었습니다.",
                        "accessToken", accessToken,
                        "tokenType", "Bearer",
                        "statusCode", 200
                ));
    }

    @DeleteMapping("/resign")
    public ResponseEntity<Map<String, Object>> withdraw(@AuthenticationPrincipal String email, @Valid @RequestBody WithdrawRequest request) {
        userService.withdraw(email, request);

        return ResponseEntity
                .ok(Map.of(
                        "message", "회원 탈퇴가 완료되었습니다.",
                        "statusCode", 200
                ));
    }
}