package com.example.koas.domain.user.dto.response;


import com.example.koas.domain.user.entity.Users;

public record UserResponseDto(
        String name,
        String department,
        String position,
        String birthDate,
        String phone
)
{
    public static UserResponseDto from(Users users) {
        return new UserResponseDto(
                users.getName(),
                users.getPosition(),
                users.getDepartment(),
                users.getBirthDate(),
                users.getPhone()
        );
    }
}
