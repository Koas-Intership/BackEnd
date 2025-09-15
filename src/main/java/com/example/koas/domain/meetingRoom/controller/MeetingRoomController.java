package com.example.koas.domain.meetingRoom.controller;


import com.example.koas.domain.meetingRoom.dto.MeetingRoomDto;
import com.example.koas.domain.meetingRoom.dto.ReservationCreateDto;
import com.example.koas.domain.meetingRoom.dto.ReservationRequestDto;
import com.example.koas.domain.meetingRoom.dto.ReservationResponseDto;
import com.example.koas.domain.meetingRoom.service.MeetingRoomService;
import com.example.koas.domain.meetingRoom.service.RoomReservationService;
import com.example.koas.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-room")
@RequiredArgsConstructor
public class MeetingRoomController
{
    private final RoomReservationService roomReservationService;
    private final MeetingRoomService meetingRoomService;
    private final AuthService authService;

    @PostMapping("reservation")
    public ResponseEntity<ReservationResponseDto> reserve(@RequestBody ReservationCreateDto reservationDto)
    {
        return ResponseEntity.ok(roomReservationService.reserve(reservationDto,authService.getUserId()));
    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        roomReservationService.cancelReservation(reservationId,authService.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<MeetingRoomDto>> getAllMeetingRooms()
    {
        return ResponseEntity.ok(meetingRoomService.findAll());
    }

    @GetMapping("reservation/all")
    public ResponseEntity<List<ReservationResponseDto>> getAllReservation()
    {
        return ResponseEntity.ok(roomReservationService.findAll());
    }

    @GetMapping("reservation/mine")
    public ResponseEntity<List<ReservationResponseDto>> getAllMyReservation()
    {
        return ResponseEntity.ok(roomReservationService.findMyReservations(authService.getUserId()));
    }

}
