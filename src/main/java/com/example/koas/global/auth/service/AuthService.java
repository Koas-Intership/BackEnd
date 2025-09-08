package com.example.koas.global.auth.service;


import com.example.koas.global.auth.RefreshToken.RefreshToken;
import com.example.koas.global.auth.RefreshToken.RefreshTokenRepository;
import com.example.koas.global.auth.dto.response.TokenResponse;
import com.example.koas.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final Duration REFRESH_TOKEN_EXP = Duration.ofDays(1);
    private static final Duration ACCESS_TOKEN_EXP = Duration.ofHours(1);

    @Transactional
    public TokenResponse login(Long id) {

        String accessToken = jwtProvider.generateToken(id,ACCESS_TOKEN_EXP);
        String refreshToken = jwtProvider.generateToken(id,REFRESH_TOKEN_EXP);

        refreshTokenRepository.save(RefreshToken.of(refreshToken));

        return TokenResponse.of(accessToken,refreshToken);
    }
}
