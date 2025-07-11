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

    public Users sessionAvailabilityCheck(String id) {
        UUID uuid = UUID.fromString(id);
        Sessions session = sessionsRepository.findById(uuid);
        return usersRepository.findById(session.getUserId());

    }

    public boolean dataVerification(Users user, String username, String password) {

        if (user.getLogin().equals(username) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
