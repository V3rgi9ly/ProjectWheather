package com.example.springexample.repository;

import com.example.springexample.model.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsRepository extends JpaRepository<Locations, Long> {
}
