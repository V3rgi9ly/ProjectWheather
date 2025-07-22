package com.example.springexample.dto;


import com.example.springexample.model.GeocodingModel.Country;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCityDto {

    @JsonProperty("name")
    private String city;

    @JsonProperty("sys")
    private Country country;

    @JsonProperty("coord")
    private CoordinateDto coordinate;

    @Override
    public String toString() {
        return "Weather{" +
                ", city='" + city + '\'' +
                ", coordinate=" + coordinate +
                ", country='" + country + '\'' +
                '}';
    }
}
