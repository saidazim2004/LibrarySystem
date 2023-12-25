package com.example.cosinuslibrarysystem.repository;

import com.example.cosinuslibrarysystem.entity.Qavat;
import com.example.cosinuslibrarysystem.entity.Shkaf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShkafRepository extends JpaRepository<Shkaf , UUID> {

    Shkaf deleteByIdAndQavat(UUID id , Qavat qavat);
}
