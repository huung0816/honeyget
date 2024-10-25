package com.example.honeyget.controller;

import com.example.honeyget.dto.JwtResponse;
import com.example.honeyget.security.JwtTokenProvider;
import com.example.honeyget.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 사용자 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserId(),
                            loginRequest.getUserPass()
                    )
            );

            // 인증 성공 시, SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 토큰 생성
            String token = tokenProvider.createToken(authentication);

            // 토큰과 함께 성공 응답 반환
            return ResponseEntity.ok(new JwtResponse(token));

        } catch (BadCredentialsException e) {
            // 인증 실패 시, 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
