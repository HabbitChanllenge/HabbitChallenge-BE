package org.example.saessakroutine.user;

import jakarta.validation.Valid;
import org.example.saessakroutine.dto.LoginRequest;
import org.example.saessakroutine.dto.MyPageResponse;
import org.example.saessakroutine.dto.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
