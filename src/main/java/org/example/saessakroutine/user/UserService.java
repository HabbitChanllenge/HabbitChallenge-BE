package org.example.saessakroutine.user;

import org.example.saessakroutine.dto.LoginRequest;
import org.example.saessakroutine.dto.MyPageResponse;
import org.example.saessakroutine.exception.PasswordMismatchException;
import org.example.saessakroutine.exception.UserAlreadyExistsException;
import org.example.saessakroutine.exception.UserNotFoundException;
import org.example.saessakroutine.jwt.JwtTokenProvider;
import org.springframework.transaction.annotation.Transactional;
import org.example.saessakroutine.dto.SignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(
            UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public void signup(SignupRequest request) {

        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .userId(request.userId())
                .password(encodedPassword)
                .email(request.email())
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(PasswordMismatchException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new PasswordMismatchException();
        }

        return jwtTokenProvider.createAccessToken(user.getEmail());
    }

    @Transactional
    public MyPageResponse getMyPage(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new MyPageResponse(
                user.getId(),
                user.getUserId(),
                user.getEmail(),
                200
        );
    }
}
