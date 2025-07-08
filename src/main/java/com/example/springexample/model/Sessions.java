package com.example.springexample.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions")
public class Sessions {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "UserId")
    private int userId;

    @Column(name = "ExpiresAt")
    private LocalDateTime expiresAt;

}
