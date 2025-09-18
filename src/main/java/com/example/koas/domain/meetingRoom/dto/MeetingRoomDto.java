package com.example.koas.domain.meetingRoom.dto;

import com.example.koas.domain.meetingRoom.entitiy.MeetingRoom;

public record MeetingRoomDto(
        Long id,
        String name,
        int capacity,
        int floor
)
{
    public static MeetingRoomDto of(MeetingRoom meetingRoom) {
        return new MeetingRoomDto(
                meetingRoom.getId(),
                meetingRoom.getName(),
                meetingRoom.getCapacity(),
                meetingRoom.getFloor()
        );
    }

    public MeetingRoom toEntity() {
        return MeetingRoom.builder()
                .name(this.name)
                .capacity(this.capacity)
                .floor(this.floor)
                .build();
    }
}
