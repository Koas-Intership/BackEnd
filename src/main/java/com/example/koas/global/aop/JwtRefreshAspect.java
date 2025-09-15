package com.example.koas.global.aop;

import com.example.koas.domain.user.repository.UserRepository;
import com.example.koas.global.auth.RefreshToken.RefreshToken;
import com.example.koas.global.auth.RefreshToken.RefreshTokenRepository;
import com.example.koas.global.auth.jwt.JwtProvider;
import com.example.koas.global.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class JwtRefreshAspect {

    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object refreshAccessToken(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getResponse();

        String accessToken = jwtProvider.resolveAccessToken(request);
        if(accessToken == null)  return joinPoint.proceed();
        if (jwtProvider.isExpired(accessToken)) {
            Long userId = authService.getUserId();
            RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Refresh Token not found"));

            if (refreshToken!=null && jwtProvider.validateToken(refreshToken.getRefreshToken())) {

                String newAccessToken = jwtProvider.generateToken(userId,"users", Duration.ofHours(1));
                response.setHeader("Authorization", "Bearer " + newAccessToken);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return null;
            }
        }

        return joinPoint.proceed();
    }
}