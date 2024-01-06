package com.example.cosinuslibrarysystem.service.polka;

import com.example.cosinuslibrarysystem.entity.Polka;
import com.example.cosinuslibrarysystem.entity.Qavat;
import com.example.cosinuslibrarysystem.entity.Shkaf;
import com.example.cosinuslibrarysystem.repository.PolkaRepository;
import com.example.cosinuslibrarysystem.repository.QavatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QavatServiceImpl implements  QavatService {

    private final QavatRepository qavatRepository ;
    @Override
    public ArrayList<Polka> findEmptySpace(UUID qavatId) {

       Qavat qavat = qavatRepository.findEmptySpace(qavatId);

       ArrayList<Polka> polkas = new ArrayList<>() ;



            for (Shkaf shkaf : qavat.getShkaflar()) {

                for (Polka polka : shkaf.getPolkalar()) {
                    if (!polka.isBookHas()){
                        polkas.add(polka);
                    }
                }
            }
        return polkas;
        }


}

