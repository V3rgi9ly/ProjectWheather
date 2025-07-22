package com.example.springexample.model;

import com.example.springexample.model.GeocodingModel.Coordinate;
import com.example.springexample.model.GeocodingModel.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoCity {

    @JsonProperty("name")
    private String city;

    @JsonProperty("sys")
    private Country country;

    @JsonProperty("coord")
    private Coordinate coordinate;

    @Override
    public String toString() {
        return "Weather{" +
                ", city='" + city + '\'' +
                ", coordinate=" + coordinate +
                ", country='" + country + '\'' +
                '}';
    }
}
