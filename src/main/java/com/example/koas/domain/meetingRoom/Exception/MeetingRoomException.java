package com.example.koas.domain.meetingRoom.Exception;

import com.example.koas.global.exception.ErrorCode;
import com.example.koas.global.exception.GlobalException;

public class MeetingRoomException extends GlobalException {
    public MeetingRoomException(ErrorCode errorCode) {
      super(errorCode);
    }
}
