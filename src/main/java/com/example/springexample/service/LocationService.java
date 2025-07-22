package com.example.springexample.service;

import com.example.springexample.dto.WeathersDto;
import com.example.springexample.model.Locations;
import com.example.springexample.model.Sessions;
import com.example.springexample.repository.LocationsRepository;
import com.example.springexample.repository.SessionsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationsRepository locationsRepository;
    private final SessionsRepository sessionsRepository;
    private final ObjectMapper objectMapper;

    public void saveLocation(WeathersDto weathersDto, String id) {

        UUID uuid = UUID.fromString(id);
        Sessions sessions = sessionsRepository.findById(uuid);
        Locations locations = new Locations();
        locations.setName(weathersDto.getName());
        locations.setLatitude(BigDecimal.valueOf(weathersDto.getCoordinate().getLatitude()));
        locations.setLongitude(BigDecimal.valueOf(weathersDto.getCoordinate().getLongitude()));
        locations.setUserId(sessions.getUserId());

        locationsRepository.save(locations);
    }
}
