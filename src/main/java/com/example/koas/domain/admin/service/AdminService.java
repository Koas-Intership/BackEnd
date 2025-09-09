package com.example.koas.domain.admin.service;

import com.example.koas.domain.admin.Exception.AdminException;
import com.example.koas.domain.admin.dto.AdminLoginRequest;
import com.example.koas.domain.admin.entity.Admin;
import com.example.koas.domain.admin.repository.AdminRepository;
import com.example.koas.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;


    public Admin login(AdminLoginRequest request)
    {

        Admin admin = adminRepository.findByEmail(request.email())
                .orElseThrow(() -> new AdminException(ErrorCode.DATA_NOT_FOUND,"Email이 "+request.email()+"인"));

        if(admin.isPasswordMatch(request.password()))
            return admin;
        else
            throw new AdminException(ErrorCode.INVALID_PASSWORD);
    }

}