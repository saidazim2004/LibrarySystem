package com.example.cosinuslibrarysystem.repository;

import com.example.cosinuslibrarysystem.entity.Qavat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface QavatRepository extends JpaRepository<Qavat ,UUID> {
    @Query(value = "SELECT q FROM Qavat q WHERE q.id = : qavat", nativeQuery = false)
    Qavat findEmptySpace(@Param("id") UUID id);
}
