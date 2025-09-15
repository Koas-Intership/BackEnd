package com.example.koas.meetingRoom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.koas.domain.meetingRoom.Exception.MeetingRoomException;
import com.example.koas.domain.meetingRoom.dto.ReservationCreateDto;
import com.example.koas.domain.meetingRoom.dto.ReservationRequestDto;
import com.example.koas.domain.meetingRoom.dto.ReservationResponseDto;
import com.example.koas.domain.meetingRoom.entitiy.MeetingRoom;
import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;
import com.example.koas.domain.meetingRoom.repository.MeetingRoomRepository;
import com.example.koas.domain.meetingRoom.repository.RoomReservationRepository;
import com.example.koas.domain.meetingRoom.service.RoomReservationService;
import com.example.koas.domain.user.entity.Users;
import com.example.koas.domain.user.repository.UserRepository;
import com.example.koas.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

class RoomReservationServiceTest {

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @Mock
    private MeetingRoomRepository meetingRoomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoomReservationService roomReservationService;

    private MeetingRoom meetingRoom;
    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        meetingRoom = MeetingRoom.builder()
                .id(1L)
                .name("회의실 A")
                .capacity(10)
                .floor(3)
                .build();

        user = Users.builder()
                .id(1L)
                .name("홍길동")
                .build();
    }

    @Test
    void reserve_success() {
        // given
        ReservationCreateDto dto = new ReservationCreateDto(
                1L,
                8,
                "회의",
                LocalDate.of(2025, 9, 9),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );


        when(meetingRoomRepository.findById(1L)).thenReturn(Optional.of(meetingRoom));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roomReservationRepository.findTopByMeetingRoomIdAndReservationDateOrderByEndTimeDesc(meetingRoom.getId(), dto.reservationDate()))
                .thenReturn(Optional.empty());
        when(roomReservationRepository.save(any(RoomReservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ReservationResponseDto result = roomReservationService.reserve(dto,1L);

        assertNotNull(result);
        assertEquals("회의", result.purpose());
    }

    @Test
    void reserve_timeConflict_throwsException() {
        // given
        ReservationCreateDto dto = new ReservationCreateDto(
                1L,
                8,
                "회의",
                LocalDate.of(2025, 9, 9),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );

        RoomReservation existing = RoomReservation.builder()
                .reservationDate(dto.reservationDate())
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(14, 30))
                .purpose("기존 회의")
                .build();

        when(meetingRoomRepository.findById(1L)).thenReturn(Optional.of(meetingRoom));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roomReservationRepository.findTopByMeetingRoomIdAndReservationDateOrderByEndTimeDesc(meetingRoom.getId(), dto.reservationDate()))
                .thenReturn(Optional.of(existing));

        // when & then
        MeetingRoomException ex = assertThrows(MeetingRoomException.class, () -> {
            roomReservationService.reserve(dto,1L);
        });

        assertEquals(ErrorCode.TIME_CONFLICT, ex.getErrorCode());
    }
}