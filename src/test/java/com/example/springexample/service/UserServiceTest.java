//package com.example.springexample.service;
//
//import com.example.springexample.dto.UsersDto;
//import com.example.springexample.model.Sessions;
//import com.example.springexample.repository.SessionsRepository;
//import com.example.springexample.repository.UsersRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class UserServiceTest {
//    @Autowired
//    private UsersService usersService;
//
//    @Autowired
//    private SessionsService sessionsService;
//
//    @Autowired
//    private SessionsRepository sessionsRepository;
//
//    @Test
//    @DisplayName("add User test")
//    public void addUserTest() {
//        UsersDto dto = usersService.addUsers("test", "test");
//        assertEquals("test", dto.getLogin());
//    }
//
//    @Test
//    @DisplayName("create sessions")
//    public void createSessionsTest() {
//        UsersDto dto = usersService.addUsers("test", "test");
//        String uuid=sessionsService.createSessions(dto);
//        assertNotNull(uuid);
//        Sessions sessions=sessionsRepository.findById(UUID.fromString(uuid));
//        assertNotNull(sessions);
//    }
//
//    @Test
//    @DisplayName("check duplicate login test")
//    public void checkDuplicateLoginTest() {
//        UsersDto dto_One = usersService.addUsers("test", "test");
//        assertThrows(IllegalArgumentException.class, () -> {
//            usersService.addUsers("test", "test");
//        });
//    }
//
//    @Test
//    @DisplayName("session expiration test")
//    public void sessionExpirationTest() {
//        UsersDto dto = usersService.addUsers("test", "test");
//        String uuid=sessionsService.createSessions(dto);
//        assertNotNull(uuid);
//        Sessions sessions=sessionsRepository.findById(UUID.fromString(uuid));
//        assertNotNull(sessions);
//        sessions.setExpiresAt(LocalDateTime.now());
//        sessionsRepository.delete(sessions);
//        Sessions deleted=sessionsRepository.findById(UUID.fromString(uuid));
//
//        assertEquals(null, deleted);
//    }
//
//    @Test
//    @DisplayName("sessions are tied to the user test")
//    public void sessionsAreTieToTheUserTest() {
//        UsersDto dto = usersService.addUsers("test", "test");
//        String uuid=sessionsService.createSessions(dto);
//        assertNotNull(uuid);
//        Sessions sessions=sessionsRepository.findById(UUID.fromString(uuid));
//
//        assertEquals(dto.getId(), sessions.getUser().getId());
//    }
//
//    @Test
//    @DisplayName("create session for non-existent user test")
//    public void createSessionForNonExistentUserTest() {
//        UsersDto dto=new UsersDto();
//        dto.setLogin("test");
//        dto.setId(1);
//        assertThrows(IllegalArgumentException.class, () -> {sessionsService.createSessions(dto);});
//    }
//
//
//}
