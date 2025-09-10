package com.example.koas.domain.meetingRoom.entitiy;

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
public class MeetingRoom
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int floor;



    public static MeetingRoom of(String name, int capacity, int floor)
    {
        return MeetingRoom.builder()
                .name(name)
                .floor(floor)
                .capacity(capacity)
                .build();
    }

}
