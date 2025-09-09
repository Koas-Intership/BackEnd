package com.example.koas.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private int age;

    @Column(length = 10, nullable = false)
    private String position;

    @Column(length = 20, nullable = false)
    private String department;


    public static Users of(String email, String password, String name, int age, String position, String department) {
        return Users.builder()
                .email(email)
                .password(password)
                .name(name)
                .age(age)
                .position(position)
                .department(department)
                .build();
    }

    public boolean isPasswordMatch(String rawPassword) {
        return this.password.equals(rawPassword);
    }
}
