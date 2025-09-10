package com.example.koas.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String email;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 10, nullable = false)
    private String birthDate;

    @Column(length = 10, nullable = false)
    private String position;

    @Column(length = 20, nullable = false)
    private String department;

    @Column(length = 20, nullable = false)
    private String phone;


    public static Users of(String email, String password, String name
            , String birthDate, String position, String department, String phone) {
        return Users.builder()
                .email(email)
                .password(password)
                .name(name)
                .birthDate(birthDate)
                .position(position)
                .department(department)
                .phone(phone)
                .build();
    }

    public boolean isPasswordMatch(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
