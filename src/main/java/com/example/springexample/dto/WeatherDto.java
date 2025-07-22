package com.example.springexample.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty("main")
    private String mainConditionWeather;

    @JsonProperty("icon")
    private String iconWeather;

    @JsonProperty("description")
    private String description;

    @Override
    public String toString() {
        return "WeatherDto{" +
                "mainConditionWeather='" + mainConditionWeather + '\'' +
                ", iconWeather='" + iconWeather + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
