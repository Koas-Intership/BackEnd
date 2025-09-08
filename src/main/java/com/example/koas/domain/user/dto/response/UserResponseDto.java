package com.example.koas.domain.user.dto.response;


import com.example.koas.domain.user.entity.Users;

public record UserResponseDto(
        String name
)
{
    public static UserResponseDto from(Users users) {
        return new UserResponseDto(
                users.getName()
        );
    }
}
