package org.example.saessakroutine.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // 로그인할 사용자 조회

    boolean existsByEmail(String email); // 회원가입 이메일 중복 검사
}
