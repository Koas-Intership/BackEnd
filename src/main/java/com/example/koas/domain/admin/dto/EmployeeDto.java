package com.example.koas.domain.admin.dto;

import com.example.koas.domain.admin.entity.Employee;

public record EmployeeDto(
         String name,
         String phoneNumber,
         String birthDate
) {
    public static Employee toEntity(
            EmployeeDto employeeDto
    )
    {
        return Employee.builder()
                .birthDate(employeeDto.birthDate())
                .phoneNumber(employeeDto.phoneNumber())
                .name(employeeDto.name())
                .build();
    }
}
