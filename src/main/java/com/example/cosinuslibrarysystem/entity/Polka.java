package com.example.cosinuslibrarysystem.entity;

// Polka.java

// Polka.java
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Polka extends BaseEntity {

    private double polkaNumber ;
    @ManyToOne
    @JoinColumn(name = "shkaf_id")
    private Shkaf shkaf;

    @Column(nullable = false)
    @OneToMany
    private List<Book> book ;


    // Constructors, additional methods, and annotations as needed
}

