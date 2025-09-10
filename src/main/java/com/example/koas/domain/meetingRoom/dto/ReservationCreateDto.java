package com.example.koas.domain.meetingRoom.dto;


import com.example.koas.domain.meetingRoom.entitiy.MeetingRoom;
import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;
import com.example.koas.domain.user.entity.Users;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalTime;


public record ReservationCreateDto(
        Long meetingRoomId,
        int number,
        String purpose,
        LocalDate reservationDate,
        LocalTime startTime,
        LocalTime endTime
) {

    public static RoomReservation toEntity(
            ReservationCreateDto dto,
            MeetingRoom meetingRoom,
            Users user
    ) {
        return RoomReservation.builder()
                .meetingRoomId(meetingRoom.getId())
                .meetingRoomName(meetingRoom.getName())
                .userId(user.getId())
                .userName(user.getName())
                .number(dto.number())
                .purpose(dto.purpose())
                .reservationDate(dto.reservationDate())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .build();
    }
}
