package com.pms.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.config.JwtConfig;
import com.pms.entity.RefreshToken;
import com.pms.entity.UserInfo;
import com.pms.repository.RefreshTokenRepository;
import com.pms.repository.UserInfoRepository;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    
    private final JwtConfig jwtConfig;

    @Autowired
    public RefreshTokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public RefreshToken createRefreshToken(String username) {
        // Fetch the user
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if (userInfo == null) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }

        // Create and save the refresh token
        RefreshToken refreshToken = new RefreshToken(
            UUID.randomUUID().toString(),
            Instant.now().plusMillis(jwtConfig.getRefteshTokenExpirationTime()), 
            userInfo
        );

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
