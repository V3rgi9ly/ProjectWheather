package com.example.springexample.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class SessionsDto {

    private UUID id;
}
