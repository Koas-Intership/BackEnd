package com.example.koas.domain.meetingRoom.service;


import com.example.koas.domain.meetingRoom.Exception.MeetingRoomException;
import com.example.koas.domain.meetingRoom.dto.ReservationCreateDto;
import com.example.koas.domain.meetingRoom.dto.ReservationRequestDto;
import com.example.koas.domain.meetingRoom.dto.ReservationResponseDto;
import com.example.koas.domain.meetingRoom.dto.ReservationSearchDto;
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

import java.time.LocalDate;
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
    public ReservationResponseDto reserve(ReservationCreateDto reservationCreateDto, Long userId) {


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

        return ReservationResponseDto
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
    public List<ReservationResponseDto> findAll()
    {
        return roomReservationRepository.findAll().stream().map(ReservationResponseDto::of).collect(Collectors.toList());
    }

    public List<ReservationResponseDto> findMyReservations(Long userId)
    {
        return roomReservationRepository.findAllByUserId(userId).stream().map(ReservationResponseDto::of).collect(Collectors.toList());
    }
    public List<ReservationResponseDto> getReservationsByDate(ReservationSearchDto searchDto) {
        if (searchDto.day() != null && !searchDto.day().isEmpty()) {
            LocalDate date = LocalDate.of(
                    Integer.parseInt(searchDto.year()),
                    Integer.parseInt(searchDto.month()),
                    Integer.parseInt(searchDto.day())
            );
            return roomReservationRepository.findAllByReservationDateOrderByReservationDateAscStartTimeAsc(date).stream()
                    .map(ReservationResponseDto::of)
                    .collect(Collectors.toList());
        } else {
            int year = Integer.parseInt(searchDto.year());
            int month = Integer.parseInt(searchDto.month());
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            return roomReservationRepository.findAllByReservationDateBetweenOrderByReservationDateAscStartTimeAsc(startDate, endDate).stream()
                    .map(ReservationResponseDto::of)
                    .collect(Collectors.toList());
        }
    }
}
