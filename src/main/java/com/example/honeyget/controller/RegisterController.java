package com.example.honeyget.controller;

import com.example.honeyget.dto.userDTO;
import com.example.honeyget.security.JwtTokenProvider; // JWT 토큰 생성기
import com.example.honeyget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")

public class RegisterController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager; // 인증 매니저

    @Autowired
    private JwtTokenProvider tokenProvider; // JWT 토큰 생성기

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody userDTO userDTO) {
        String userId = userDTO.getUserId();
        String userPass = userDTO.getUserPass();
        service.createUser(userId, userPass);
        return ResponseEntity.ok("회원가입 성공");
    }

}
