package com.example.koas.global.auth.RefreshToken;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @Column(nullable = false)
    private Long userId;


    public static RefreshToken of(String refreshToken) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
