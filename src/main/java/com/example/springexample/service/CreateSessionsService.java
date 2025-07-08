package com.example.springexample.service;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.model.Sessions;
import com.example.springexample.repository.SessionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateSessionsService {
    private final SessionsRepository sessionsRepository;


    public String createSessions(UsersDto usersDto) {
        LocalDateTime localDateTime = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        sessionsRepository.save(new Sessions(uuid, usersDto.getId(),localDateTime.plusMinutes(30)));
        return uuid.toString();
    }
}
