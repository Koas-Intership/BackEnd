package com.example.koas.domain.user.service;



import com.example.koas.domain.admin.Exception.AdminException;
import com.example.koas.domain.admin.dto.AdminLoginRequest;
import com.example.koas.domain.admin.dto.EmployeeDto;
import com.example.koas.domain.admin.entity.Admin;
import com.example.koas.domain.admin.entity.Employee;
import com.example.koas.domain.admin.repository.EmployeeRepository;
import com.example.koas.domain.user.dto.request.UserLoginRequestDto;
import com.example.koas.domain.user.dto.request.UserRegisterDto;
import com.example.koas.domain.user.dto.response.UserResponseDto;
import com.example.koas.domain.user.entity.Users;
import com.example.koas.domain.user.exception.UserException;
import com.example.koas.domain.user.repository.UserRepository;
import com.example.koas.global.aop.ThrowIfEmpty;
import com.example.koas.global.auth.dto.response.TokenResponse;
import com.example.koas.global.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService
{
    final private UserRepository usersRepository;
    final private EmployeeRepository employeeRepository;
    final private PasswordEncoder passwordEncoder;
    @ThrowIfEmpty(exception = UserException.class)
    public List<UserResponseDto> findAll()
    {
        return toDtoList(usersRepository.findAll());
    }

    //@ThrowIfEmpty
   //public List<UserResponseDto> findDeletedUsers() {}

    private List<UserResponseDto> toDtoList(List<Users> users)
    {
            return users.stream()
                    .map(UserResponseDto::from)
                    .toList();
    }




    @Transactional(readOnly = true)
    public void verifyEmployee(EmployeeDto employeeDto) {
        Employee employee= employeeRepository.findByNameAndPhoneNumberAndBirthDate(
                employeeDto.name(),
                employeeDto.phoneNumber(),
                employeeDto.birthDate()
        ).orElseThrow(() -> new UserException(ErrorCode.DATA_NOT_FOUND));

        if(employee.isRegistered())
            throw new UserException(ErrorCode.ALREADY_REGISTERED);
        employee.setRegistered(true);
    }

    @Transactional
    public UserResponseDto register(UserRegisterDto dto) {

        Users user = Users.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .name(dto.name())
                .birthDate(dto.birthDate())
                .position(dto.position())
                .department(dto.department())
                .phone(dto.phoneNumber())
                .build();

        return UserResponseDto.from(usersRepository.save(user));
    }

    public Users login(UserLoginRequestDto request)
    {

        Users users = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserException(ErrorCode.DATA_NOT_FOUND,"Email이 "+request.email()+"인 유저 계정이 존재 하지 않습니다."));

        if(users.isPasswordMatch(request.password(),passwordEncoder))
            return users;
        else
            throw new AdminException(ErrorCode.INVALID_PASSWORD);
    }

    public UserResponseDto getCurrentUser(Long userId)
    {
        return UserResponseDto.from(usersRepository.findById(userId).orElseThrow(() -> new UserException(ErrorCode.DATA_NOT_FOUND)));
    }

    public void changePassword(Long userId, String newPassword) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.DATA_NOT_FOUND,"Id가 "+userId+"인 유저 계정이 존재 하지 않습니다."));

        user.updatePassword(passwordEncoder.encode(newPassword));
    }
}
