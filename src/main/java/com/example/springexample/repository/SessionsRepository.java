package com.example.springexample.repository;

import com.example.springexample.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<Sessions, Long> {
}
