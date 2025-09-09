package com.example.koas.domain.meetingRoom.service;


import com.example.koas.domain.meetingRoom.dto.MeetingRoomDto;
import com.example.koas.domain.meetingRoom.dto.ReservationDto;
import com.example.koas.domain.meetingRoom.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingRoomService
{
    private final MeetingRoomRepository meetingRoomRepository;

    @Transactional
    public MeetingRoomDto register (MeetingRoomDto meetingRoomDto)
    {
        meetingRoomRepository.save(meetingRoomDto.toEntity());
        return meetingRoomDto;
    }

}
