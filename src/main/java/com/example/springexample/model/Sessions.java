package com.example.springexample.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "sessions")
public class Sessions {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "UserId")
    private int userId;

    @Column(name = "ExpiresAt")
    private Date expiresAt;

}
