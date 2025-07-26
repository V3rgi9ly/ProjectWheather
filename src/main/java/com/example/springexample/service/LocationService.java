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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationsRepository locationsRepository;
    private final SessionsRepository sessionsRepository;
    private final WheatherService wheatherService;
    private final ObjectMapper objectMapper;

    public void saveLocation(WeathersDto weathersDto, String id) {

        UUID uuid = UUID.fromString(id);
        Sessions sessions = sessionsRepository.findById(uuid);
        Locations locations = new Locations();
        locations.setName(weathersDto.getName());
        locations.setLatitude(BigDecimal.valueOf(weathersDto.getLatitude()));
        locations.setLongitude(BigDecimal.valueOf(weathersDto.getLongitude()));
        locations.setUserId(sessions.getUserId());

        locationsRepository.save(locations);
    }

    public List<WeathersDto> getWeather(String id) {
        UUID uuid = UUID.fromString(id);
        Sessions sessions = sessionsRepository.findById(uuid);
        List<Locations> locations=locationsRepository.findByUserId(sessions.getUserId());
        List<Optional<WeathersDto>> listWeatherDto = new ArrayList<>();
        Optional<WeathersDto> weathersDto;

        for (Locations location : locations) {
            weathersDto=wheatherService.getWeather(Double.parseDouble(String.valueOf(location.getLatitude())), Double.parseDouble(String.valueOf(location.getLongitude())));
            listWeatherDto.add(weathersDto);
        }


        List<WeathersDto> unpackedWeatherDtos = listWeatherDto.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();


        return unpackedWeatherDtos;
    }

    public void deleteLocation(String name, String cookie) {
        UUID uuid = UUID.fromString(cookie);
        Sessions sessions = sessionsRepository.findById(uuid);
        Locations locations = locationsRepository.findByName(name);

        locationsRepository.deleteById((long) locations.getId());
    }

    public boolean сheckingForDuplicates(Optional<WeathersDto> weathersDto, String id){
        UUID uuid = UUID.fromString(id);
        Sessions sessions = sessionsRepository.findById(uuid);
        Optional<Locations> locations= Optional.ofNullable(locationsRepository.findByName(weathersDto.get().getName()));
        if (locations.isPresent()){
            if (weathersDto.get().getName().equals(locations.get().getName()) && locations.get().getUserId()== sessions.getUserId() ){
                return false;
            } else {
                return true;
            }
        }else {
            return true;
        }

    }
}
