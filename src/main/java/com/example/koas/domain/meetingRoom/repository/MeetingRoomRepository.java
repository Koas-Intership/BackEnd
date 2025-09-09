package com.example.koas.domain.meetingRoom.repository;

import com.example.koas.domain.meetingRoom.entitiy.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long>
{
}
