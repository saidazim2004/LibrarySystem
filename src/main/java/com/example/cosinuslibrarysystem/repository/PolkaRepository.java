package com.example.cosinuslibrarysystem.repository;

import com.example.cosinuslibrarysystem.entity.Polka;
import com.example.cosinuslibrarysystem.entity.Qavat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.UUID;

public interface PolkaRepository extends JpaRepository<Polka , UUID> {

//

}
