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
public class WeathersDto {


    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("main")
    private String mainConditionWeather;

    @JsonProperty("icon")
    private String iconWeather;

    @JsonProperty("description")
    private String description;

    @JsonProperty("name")
    private String name;

    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("feels_like")
    private double feels_like_temperature;


    @Override
    public String toString() {
        return "WeathersDto{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", mainConditionWeather='" + mainConditionWeather + '\'' +
                ", iconWeather='" + iconWeather + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", feels_like_temperature=" + feels_like_temperature +
                '}';
    }
}
