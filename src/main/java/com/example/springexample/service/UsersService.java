package com.example.springexample.service;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.mapper.Mapping;
import com.example.springexample.model.Users;
import com.example.springexample.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final Mapping mapping;
    private final ObjectMapper objectMapper;

    public UsersDto addUsers(String username, String password) {
        if (usersRepository.findByLogin(username) != null) {
            throw new IllegalArgumentException("Login already exists");
        }

        Users user = new Users();
        user.setLogin(username);
        user.setPassword(password);
        usersRepository.save(user);
        return objectMapper.convertValue(usersRepository.findById(user.getId()), UsersDto.class);
    }

    public UsersDto getUser(String username) {
        Users user = usersRepository.findByLogin(username);
        return mapping.toUsersDto(user);
    }

    public boolean dataVerification(String username, String password) {
        Users user = usersRepository.findByLogin(username);

        if (user.getLogin().equals(username) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
