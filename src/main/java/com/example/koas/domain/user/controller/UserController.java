package com.example.koas.domain.user.controller;


import com.example.koas.domain.user.dto.request.UserLoginRequestDto;
import com.example.koas.domain.user.dto.response.UserResponseDto;
import com.example.koas.domain.user.entity.Users;
import com.example.koas.domain.user.service.UserService;
import com.example.koas.global.auth.dto.response.TokenResponse;
import com.example.koas.global.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid UserLoginRequestDto request
    )
    {
        Users users = userService.login(request);

        TokenResponse tokenResponse = authService.login(users.getId(),"users");

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> findAll()
    {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll());
    }
}
