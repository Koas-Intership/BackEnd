package com.example.koas.domain.meetingRoom.dto;

import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;
import com.example.koas.domain.user.dto.response.UserResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
        String userName,
        String meetingName,
        LocalDate reservationDate,
        LocalTime startTime,
        LocalTime endTime,
        String purpose
) {
    public static ReservationDto of(RoomReservation reservation) {
        return new ReservationDto(
                reservation.getUserName(),
                reservation.getMeetingRoomName(),
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getPurpose()
        );
    }
}