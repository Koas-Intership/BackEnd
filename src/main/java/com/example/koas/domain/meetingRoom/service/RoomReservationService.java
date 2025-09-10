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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomReservationService
{
    private final RoomReservationRepository roomReservationRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final UserRepository userRepository;
    @Transactional
    public ReservationDto reserve(ReservationCreateDto reservationCreateDto, Long userId) {


        MeetingRoom meetingRoom = meetingRoomRepository.findById(reservationCreateDto.meetingRoomId()).orElse(null);

        if(meetingRoom == null)
            throw new MeetingRoomException(ErrorCode.DATA_NOT_FOUND);
        if(meetingRoom.getCapacity() <reservationCreateDto.number())
        {
            throw new MeetingRoomException(ErrorCode.EXCEEDS_CAPACITY);
        }

        Optional<RoomReservation> lastReservationOpt =
                roomReservationRepository.findTopByMeetingRoomIdAndReservationDateOrderByEndTimeDesc(
                        meetingRoom.getId(),
                        reservationCreateDto.reservationDate()
                );

        lastReservationOpt.ifPresent(lastReservation -> {
            if (!reservationCreateDto.startTime().isAfter(lastReservation.getEndTime())) {
                throw new MeetingRoomException(ErrorCode.TIME_CONFLICT);
            }
        });

        Users users=userRepository.findById(userId).orElse(null);

        return ReservationDto
                .of(roomReservationRepository.save(ReservationCreateDto.toEntity(reservationCreateDto,meetingRoom,users)));

    }

    @Transactional
    public void cancelReservation(Long reservationId,Long userId) {
        RoomReservation reservation = roomReservationRepository.findById(reservationId)
                .orElseThrow(() -> new MeetingRoomException(ErrorCode.DATA_NOT_FOUND));
        if(!reservation.getUserId().equals(userId))
            throw new MeetingRoomException(ErrorCode.CANCEL_UNAUTHORIZED);
        roomReservationRepository.delete(reservation);
    }
    public List<ReservationDto> findAll()
    {
        return roomReservationRepository.findAll().stream().map(ReservationDto::of).collect(Collectors.toList());
    }

    public List<ReservationDto> findMyReservations(Long userId)
    {
        return roomReservationRepository.findAll().stream().map(ReservationDto::of).collect(Collectors.toList());
    }
}
