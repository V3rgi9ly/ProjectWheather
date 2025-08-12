package com.example.springexample.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions")
public class Sessions {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private Users user;

}
