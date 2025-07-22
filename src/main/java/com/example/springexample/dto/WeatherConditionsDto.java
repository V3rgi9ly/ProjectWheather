package com.example.springexample.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherConditionsDto {

    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("feels_like")
    private double feels_like_temperature;

    @Override
    public String toString() {
        return "WeatherConditionsDto{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", feels_like_temperature=" + feels_like_temperature +
                '}';
    }
}
