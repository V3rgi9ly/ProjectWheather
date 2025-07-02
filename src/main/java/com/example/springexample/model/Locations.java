package com.example.springexample.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="locations")
public class Locations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name ="UserId")
    private int userId;

    @Column(name = "Latitude")
    private BigDecimal latitude;

    @Column(name = "Longitude")
    private BigDecimal longitude;
}
