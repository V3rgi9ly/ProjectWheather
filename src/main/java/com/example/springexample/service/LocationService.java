package com.example.springexample.service;

import com.example.springexample.dto.WeathersDto;
import com.example.springexample.mapper.Mapping;
import com.example.springexample.model.Locations;
import com.example.springexample.model.Sessions;
import com.example.springexample.repository.LocationsRepository;
import com.example.springexample.repository.SessionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationsRepository locationsRepository;
    private final SessionsRepository sessionsRepository;
    private final WeatherService weatherService;
    private  final Mapping mapping=Mapping.mapping;

    public void saveLocation(WeathersDto weathersDto, String id) {

        UUID uuid = UUID.fromString(id);
        Sessions sessions = sessionsRepository.findById(uuid);
        Locations locations= mapping.toLocationsDto(weathersDto);
        locations.setUser(sessions.getUser());

        locationsRepository.save(locations);
    }

    public List<WeathersDto> getWeather(String id) {
        UUID uuid = UUID.fromString(id);
        Sessions sessions = sessionsRepository.findById(uuid);
        List<Locations> locations=locationsRepository.findByUserId(sessions.getUser().getId());
        List<Optional<WeathersDto>> listWeatherDto = new ArrayList<>();
        Optional<WeathersDto> weathersDto;

        for (Locations location : locations) {
            weathersDto= weatherService.getWeather(Double.parseDouble(String.valueOf(location.getLatitude())), Double.parseDouble(String.valueOf(location.getLongitude())));
            listWeatherDto.add(weathersDto);
        }

        List<WeathersDto> unpackedWeatherDtos = listWeatherDto.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();


        return unpackedWeatherDtos;
    }

    public void deleteLocation(String name, String cookie) {
        UUID uuid= UUID.fromString(cookie);
        Sessions sessions = sessionsRepository.findById(uuid);
        Locations locations = locationsRepository.findByNameAndUserId(name,sessions.getUser().getId());
        locationsRepository.deleteById((long) locations.getId());
    }

    public boolean сheckingForDuplicates(Optional<WeathersDto> weathersDto, String id){
        UUID uuid = UUID.fromString(id);
        Sessions sessions = sessionsRepository.findById(uuid);
        Optional<Locations> locations= Optional.ofNullable(locationsRepository.findByNameAndUserId(weathersDto.get().getName(), sessions.getUser().getId()));
        if (locations.isPresent()){
            if (weathersDto.get().getName().equals(locations.get().getName()) && locations.get().getUser().getId()== sessions.getUser().getId() ){
                return false;
            } else {
                return true;
            }
        }else {
            return true;
        }

    }
}
