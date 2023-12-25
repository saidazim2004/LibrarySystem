package com.example.cosinuslibrarysystem.repository;

import com.example.cosinuslibrarysystem.entity.Polka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PolkaRepository extends JpaRepository<Polka , UUID> {

}
