package com.example.springexample.repository;

import com.example.springexample.model.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {
    List<Locations> findByUserId(int userId);
    Locations findByName(String name);
}
