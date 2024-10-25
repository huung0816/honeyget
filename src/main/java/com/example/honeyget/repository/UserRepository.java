package com.example.honeyget.repository;

import com.example.honeyget.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId); // UserEntity 반환
}
