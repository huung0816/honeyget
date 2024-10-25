package com.example.honeyget.service;

import com.example.honeyget.model.CustomUser; // 사용자 모델 클래스
import com.example.honeyget.repository.UserRepository;
import com.example.honeyget.Entity.UserEntity; // UserEntity import 추가
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String user_Id) throws UsernameNotFoundException {
        // userId로 사용자 조회
        UserEntity userEntity = userRepository.findByUserId(user_Id); // UserEntity로 가져옴

        // 사용자가 없을 경우 예외 발생
        if (userEntity == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // CustomUser로 변환
        CustomUser user = new CustomUser();
        user.setUserId(userEntity.getUserId());
        user.setUserPass(userEntity.getUserPass());
        // 추가 필드가 있다면 여기에 설정

        // UserDetails에 맞는 객체 생성
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getUserPass(),
                new ArrayList<>()
        );
    }
}
