package com.example.springexample.service;

import com.example.springexample.dto.SessionsDto;
import com.example.springexample.model.Sessions;
import com.example.springexample.repository.SessionsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionsService {
    private final SessionsRepository sessionsRepository;
    private final ObjectMapper objectMapper;

    public SessionsDto getSession(String sessionValue) {

        UUID sessionId = UUID.fromString(sessionValue);
        Sessions session=sessionsRepository.findById(sessionId);
        SessionsDto  sessionsDto=objectMapper.convertValue(session,SessionsDto.class);

        return sessionsDto;
    }

    public boolean expireSession(String sessionValue) {
        UUID sessionId = UUID.fromString(sessionValue);
        LocalDateTime localDateTime = LocalDateTime.now();
        Sessions session=sessionsRepository.findById(sessionId);

        if (localDateTime.isAfter(session.getExpiresAt()) ){
            return true;
        }
        else if(localDateTime.isBefore(session.getExpiresAt())){
            return false;
        }
        return false;
    }
}
