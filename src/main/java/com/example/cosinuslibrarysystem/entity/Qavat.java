package com.example.cosinuslibrarysystem.entity;

// Qavat.java

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

// Qavat.java
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Qavat extends BaseEntity {
    private int qavat ;

    @OneToMany(mappedBy = "qavat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shkaf> shkaflar = new ArrayList<>();

    // Constructors, additional methods, and annotations as needed
}
