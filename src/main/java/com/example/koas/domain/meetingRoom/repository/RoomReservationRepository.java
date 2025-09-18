package com.example.koas.domain.meetingRoom.repository;

import com.example.koas.domain.meetingRoom.entitiy.MeetingRoom;
import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long>
{
    Optional<RoomReservation> findTopByMeetingRoomIdAndReservationDateOrderByEndTimeDesc(
            Long meetingRoomId, LocalDate reservationDate
    );
    List<RoomReservation> findAllByReservationDateOrderByReservationDateAscStartTimeAsc(LocalDate reservationDate);

    List<RoomReservation> findAllByUserId(Long userId);

    List<RoomReservation> findAllByReservationDateBetweenOrderByReservationDateAscStartTimeAsc(LocalDate start, LocalDate end);



}
