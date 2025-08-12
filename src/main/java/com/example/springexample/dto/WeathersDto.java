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


    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
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


}
