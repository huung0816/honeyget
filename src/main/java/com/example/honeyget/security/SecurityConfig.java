package com.example.honeyget.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/api/register")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/ProductWrite")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/products")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/my/products")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/producted/{id}", "PUT")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/producted/{id}", "DELETE")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/ProductEx/{id}")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auction")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auction/**")).permitAll()
                        .requestMatchers("/api/uploads/**").permitAll()
                        .requestMatchers("/uploads/**", "/static/**", "/css/**", "/js/**", "/images/**","/png/**","/jpg/**","/JPG/**").permitAll() // 정적 리소스 접근 허용
                )
                .cors(withDefaults()) // 새로운 방식으로 CORS 설정
                .headers(headers -> headers.addHeaderWriter(
                        new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)
                ));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // 사용자 인증 로직 설정 필요
        return authenticationManagerBuilder.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3001", "http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
