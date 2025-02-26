package com.pms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pms.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByRefreshToken(String token);
}