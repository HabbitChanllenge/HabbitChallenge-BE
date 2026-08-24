package org.example.saessakroutine.user;

import org.example.saessakroutine.exception.UserAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;
import org.example.saessakroutine.dto.SignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
