package com.example.koas.domain.meetingRoom.repository;

import com.example.koas.domain.meetingRoom.entitiy.MeetingRoom;
import com.example.koas.domain.meetingRoom.entitiy.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long>
{
    Optional<RoomReservation> findTopByMeetingRoomAndReservationDateOrderByEndTimeDesc(
            MeetingRoom meetingRoom, LocalDate reservationDate
    );
}
