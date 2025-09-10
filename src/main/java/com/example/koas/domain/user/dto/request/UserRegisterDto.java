package com.example.koas.domain.user.dto.request;

public record UserRegisterDto(
        String email,
        String password,
        String name,
        String position,
        String department,
        String phoneNumber,
        String birthDate
) {}
