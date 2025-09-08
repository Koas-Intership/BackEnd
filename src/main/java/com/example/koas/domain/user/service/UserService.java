package com.example.koas.domain.user.service;



import com.example.koas.domain.user.dto.request.UserLoginRequestDto;
import com.example.koas.domain.user.dto.response.UserResponseDto;
import com.example.koas.domain.user.entity.Users;
import com.example.koas.domain.user.exception.UserException;
import com.example.koas.domain.user.repository.UserRepository;
import com.example.koas.global.aop.ThrowIfEmpty;
import com.example.koas.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService
{
    final private UserRepository usersRepository;


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


    public Users login(UserLoginRequestDto request)
    {

        Users users = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserException(ErrorCode.DATA_NOT_FOUND,"Email이 "+request.email()+"인"));

        if(users.isPasswordMatch(request.password()))
            return users;
        else
            throw new UserException(ErrorCode.INVALID_PASSWORD);
    }


}
