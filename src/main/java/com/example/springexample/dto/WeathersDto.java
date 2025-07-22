package com.example.springexample.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeathersDto {


    @JsonProperty("coord")
    private CoordinateDto coordinate;

    @JsonProperty("weather")
    private List<WeatherDto> weather;

    @JsonProperty("name")
    private String name;

    @JsonProperty("main")
    private WeatherConditionsDto weatherConditionsDto;


    @Override
    public String toString() {
        return "WeathersDto{" +
                "coordinate=" + coordinate +
                ", weather=" + weather +
                ", name='" + name + '\'' +
                ", weatherConditionsDto=" + weatherConditionsDto +
                '}';
    }
}
