package com.example.springexample.service;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.model.Users;
import com.example.springexample.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationUsersService {

    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper;

    public UsersDto addUsers(String username, String password) {
        Users user = new Users(username, password);
        usersRepository.save(user);

        UsersDto usersDto =objectMapper.convertValue(usersRepository.findById(user.getId()), UsersDto.class);
        return usersDto;
    }

}
