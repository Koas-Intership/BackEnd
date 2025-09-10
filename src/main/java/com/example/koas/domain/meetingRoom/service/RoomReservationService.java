package com.example.koas.domain.meetingRoom.service;


import com.example.koas.domain.meetingRoom.Exception.MeetingRoomException;
import com.example.koas.domain.meetingRoom.dto.ReservationCreateDto;
import com.example.koas.domain.meetingRoom.dto.ReservationDto;
import com.example.koas.domain.meetingRoom.entitiy.MeetingRoom;
import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;
import com.example.koas.domain.meetingRoom.repository.MeetingRoomRepository;
import com.example.koas.domain.meetingRoom.repository.RoomReservationRepository;
import com.example.koas.domain.user.entity.Users;
import com.example.koas.domain.user.repository.UserRepository;
import com.example.koas.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomReservationService
{
    private final RoomReservationRepository roomReservationRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final UserRepository userRepository;
    @Transactional
    public ReservationDto reserve(ReservationCreateDto reservationCreateDto) {


        MeetingRoom meetingRoom = meetingRoomRepository.findById(reservationCreateDto.meetingRoomId()).orElse(null);

        if(meetingRoom != null&&meetingRoom.getCapacity() <reservationCreateDto.number())
        {
            throw new MeetingRoomException(ErrorCode.EXCEEDS_CAPACITY);
        }

        Optional<RoomReservation> lastReservationOpt =
                roomReservationRepository.findTopByMeetingRoomAndReservationDateOrderByEndTimeDesc(
                        meetingRoom,
                        reservationCreateDto.reservationDate()
                );

        lastReservationOpt.ifPresent(lastReservation -> {
            if (!reservationCreateDto.startTime().isAfter(lastReservation.getEndTime())) {
                throw new MeetingRoomException(ErrorCode.TIME_CONFLICT);
            }
        });

        Users users=userRepository.findById(reservationCreateDto.userId()).orElse(null);
        ;
        return ReservationDto
                .of(roomReservationRepository.save(ReservationCreateDto.toEntity(reservationCreateDto,meetingRoom,users)));

    }
}
