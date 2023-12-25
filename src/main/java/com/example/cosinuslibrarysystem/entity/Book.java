package com.example.cosinuslibrarysystem.entity;

// Book.java
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private double page;
    @ManyToOne
    private Polka polka;

    private boolean isRent = false ;



}

