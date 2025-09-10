package com.example.koas.domain.meetingRoom.controller;


import com.example.koas.domain.meetingRoom.dto.MeetingRoomDto;
import com.example.koas.domain.meetingRoom.dto.ReservationCreateDto;
import com.example.koas.domain.meetingRoom.dto.ReservationDto;
import com.example.koas.domain.meetingRoom.service.MeetingRoomService;
import com.example.koas.domain.meetingRoom.service.RoomReservationService;
import com.example.koas.global.auth.jwt.JwtProvider;
import com.example.koas.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meeting-room")
@RequiredArgsConstructor
public class MeetingRoomController
{
    private final MeetingRoomService meetingRoomService;
    private final RoomReservationService roomReservationService;
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<MeetingRoomDto> register(@RequestBody MeetingRoomDto meetingRoomDto) {
        MeetingRoomDto saved = meetingRoomService.register(meetingRoomDto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("reservation")
    public ResponseEntity<ReservationDto> reserve(@RequestBody ReservationCreateDto reservationDto)
    {
        return ResponseEntity.ok(roomReservationService.reserve(reservationDto,authService.getUserId()));
    }


}
