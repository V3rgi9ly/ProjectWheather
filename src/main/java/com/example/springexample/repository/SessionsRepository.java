package com.example.springexample.repository;

import com.example.springexample.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionsRepository extends JpaRepository<Sessions, Long> {
    Sessions findById(UUID uuid);

}
