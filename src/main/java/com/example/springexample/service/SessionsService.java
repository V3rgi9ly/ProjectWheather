package com.example.springexample.service;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.model.Sessions;
import com.example.springexample.model.Users;
import com.example.springexample.repository.SessionsRepository;
import com.example.springexample.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionsService {
    private final SessionsRepository sessionsRepository;
    private final UsersRepository usersRepository;

    public String createSessions(UsersDto usersDto) {
        LocalDateTime localDateTime = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        if (usersRepository.findById(usersDto.getId())==null) {
            throw new IllegalArgumentException("user is absent");
        }else {
            Users user = usersRepository.findById(usersDto.getId());
            Sessions sessions = new Sessions(uuid, localDateTime.plusMinutes(30), user);
            sessionsRepository.save(sessions);
        }
        return uuid.toString();
    }

    public boolean expireSession(String sessionValue) {
        UUID sessionId = UUID.fromString(sessionValue);
        LocalDateTime localDateTime = LocalDateTime.now();

        Sessions session=sessionsRepository.findById(sessionId);
        if (session==null) {
            return true;
        }
        else {
            return getSessionRelevance(localDateTime, session);
        }

    }

    @Scheduled(cron = "0 30 * * * ?")
    private void endOfSession() {
        sessionsRepository.deleteAll();
    }


    public void deleteSession(String sessionValue) {
        UUID sessionId = UUID.fromString(sessionValue);
        Sessions session=sessionsRepository.findById(sessionId);
        if (session==null) {
            return;
        }

        Users user = session.getUser();
        if (user != null) {
            user.setSessions(null);
        }
        sessionsRepository.delete(session);
    }

    private boolean getSessionRelevance(LocalDateTime localDateTime, Sessions session) {
        if (localDateTime.isAfter(session.getExpiresAt()) ){
            return true;
        }
        else if(localDateTime.isBefore(session.getExpiresAt())){
            return false;
        }

        return false;
    }
}
