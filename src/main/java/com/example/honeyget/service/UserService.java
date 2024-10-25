package com.example.honeyget.service;

import com.example.honeyget.Entity.UserEntity;
import com.example.honeyget.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // BCryptPasswordEncoder 추가

    public UserEntity createUser(String userId, String userPass) {
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setUserPass(passwordEncoder.encode(userPass)); // 비밀번호 해시화

        return userRepository.save(user);
    }

    public UserEntity findUserById(Long userId) {
        // 데이터베이스에서 사용자 정보를 찾는 로직 필요
        return userRepository.findById(userId).orElse(null); // 실제 사용자 객체를 반환해야 합니다.
    }
}
