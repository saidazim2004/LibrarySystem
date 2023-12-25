package com.example.cosinuslibrarysystem.entity;



import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class Shkaf extends BaseEntity {

    private int shkafNumber ;
    @ManyToOne
    @JoinColumn(name = "qavat_id")
    private Qavat qavat;


    @OneToMany(mappedBy = "shkaf", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Polka> polkalar = new ArrayList<>();

    // Constructors, additional methods, and annotations as needed
}


