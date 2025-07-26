package com.example.springexample.dto;



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

    @JsonProperty("country")
    private String country;

    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lon")
    private double longitude;

    @Override
    public String toString() {
        return "GeoCityDto{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
