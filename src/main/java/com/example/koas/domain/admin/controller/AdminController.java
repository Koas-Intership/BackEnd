package com.example.koas.domain.admin.controller;

import com.example.koas.domain.admin.dto.AdminLoginRequest;
import com.example.koas.domain.admin.dto.EmployeeDto;
import com.example.koas.domain.admin.entity.Admin;
import com.example.koas.domain.admin.service.AdminService;
import com.example.koas.domain.meetingRoom.dto.MeetingRoomDto;
import com.example.koas.domain.meetingRoom.service.MeetingRoomService;
import com.example.koas.global.auth.dto.response.TokenResponse;
import com.example.koas.global.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController
{
    private final AuthService authService;
    private final AdminService adminService;
    private final MeetingRoomService meetingRoomService;
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid AdminLoginRequest request
    )
    {
        adminService.login(request);

        TokenResponse tokenResponse = authService.login(null, "admin");

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/register/meetingRoom")
    public ResponseEntity<MeetingRoomDto> register(@RequestBody MeetingRoomDto meetingRoomDto) {
        MeetingRoomDto saved = meetingRoomService.register(meetingRoomDto);
        return ResponseEntity.ok(saved);
    }


    @PostMapping("/register/employee")
    public ResponseEntity<EmployeeDto> register(EmployeeDto employeeCreateDto)
    {
        return ResponseEntity.ok(adminService.register(employeeCreateDto));
    }
}
