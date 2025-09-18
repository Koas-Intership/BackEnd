package com.example.koas.domain.user.controller;


import com.example.koas.domain.admin.dto.EmployeeDto;
import com.example.koas.domain.user.dto.request.UserLoginRequestDto;
import com.example.koas.domain.user.dto.request.UserRegisterDto;
import com.example.koas.domain.user.dto.response.UserResponseDto;
import com.example.koas.domain.user.entity.Users;
import com.example.koas.domain.user.service.UserService;
import com.example.koas.global.aop.RefreshTokenCheck;
import com.example.koas.global.auth.dto.response.TokenResponse;
import com.example.koas.global.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    private final AuthService authService;




    @GetMapping("/all")
    @RefreshTokenCheck
    public ResponseEntity<List<UserResponseDto>> findAll()
    {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll());
    }

    @GetMapping("/verify")
    @RefreshTokenCheck
    public ResponseEntity<Void> verifyEmployee(@RequestBody EmployeeDto employeeDto) {
        userService.verifyEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid UserLoginRequestDto request
    )
    {
        Users user=userService.login(request);

        TokenResponse tokenResponse = authService.login(user.getId(), "users");

        return ResponseEntity.ok(tokenResponse);
    }


    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody String newPassword) {

        userService.changePassword(authService.getUserId(), newPassword);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/me")
    @RefreshTokenCheck
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok( userService.getCurrentUser(authService.getUserId()));
    }
}
