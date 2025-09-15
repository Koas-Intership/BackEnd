package com.example.koas.domain.meetingRoom.service;


import com.example.koas.domain.meetingRoom.dto.MeetingRoomDto;
import com.example.koas.domain.meetingRoom.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<MeetingRoomDto> findAll() {
        return meetingRoomRepository.findAll().stream()
                .map(MeetingRoomDto::of)
                .collect(Collectors.toList());
    }

}
