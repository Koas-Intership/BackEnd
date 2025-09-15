package com.example.koas.domain.meetingRoom.dto;

import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(
        String userName,
        String meetingName,
        LocalDate reservationDate,
        LocalTime startTime,
        LocalTime endTime,
        String purpose
) {
    public static ReservationRequestDto of(RoomReservation reservation) {
        return new ReservationRequestDto(
                reservation.getUserName(),
                reservation.getMeetingRoomName(),
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getPurpose()
        );
    }
}