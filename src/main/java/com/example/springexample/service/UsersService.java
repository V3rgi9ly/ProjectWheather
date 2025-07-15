package com.example.springexample.service;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.dto.VerificationUserDto;
import com.example.springexample.model.Sessions;
import com.example.springexample.model.Users;
import com.example.springexample.repository.SessionsRepository;
import com.example.springexample.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final SessionsRepository sessionsRepository;
    private final ObjectMapper objectMapper;

    public UsersDto getUser(String username) {
        Users user=usersRepository.findByLogin(username);
        return objectMapper.convertValue(user, UsersDto.class);

    }

    public boolean dataVerification( String username, String password) {
        Users user=usersRepository.findByLogin(username);

        if (user.getLogin().equals(username) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
