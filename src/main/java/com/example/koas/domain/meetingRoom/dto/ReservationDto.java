package com.example.koas.domain.meetingRoom.dto;

import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;
import com.example.koas.domain.user.dto.response.UserResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
        MeetingRoomDto meetingRoom,
        UserResponseDto user,
        LocalDate reservationDate,
        LocalTime startTime,
        LocalTime endTime,
        String purpose
) {
    public static ReservationDto of(RoomReservation reservation) {
        return new ReservationDto(
                MeetingRoomDto.of(reservation.getMeetingRoom()),
                UserResponseDto.from(reservation.getUser()),
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getPurpose()
        );
    }
}