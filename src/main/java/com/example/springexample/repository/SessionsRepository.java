package com.example.springexample.repository;

import com.example.springexample.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionsRepository extends JpaRepository<Sessions, Long> {
    Sessions findById(UUID uuid);

    Sessions deleteById(UUID id);
}
