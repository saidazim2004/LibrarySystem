package com.example.cosinuslibrarysystem.service.polka;

import com.example.cosinuslibrarysystem.entity.Polka;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;


public interface QavatService {
    ArrayList<Polka> findEmptySpace(UUID qavat);


}
