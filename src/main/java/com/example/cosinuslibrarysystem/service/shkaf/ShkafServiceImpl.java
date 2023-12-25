package com.example.cosinuslibrarysystem.service.shkaf;

import com.example.cosinuslibrarysystem.entity.Shkaf;
import com.example.cosinuslibrarysystem.exception.DataNotFoundException;
import com.example.cosinuslibrarysystem.repository.ShkafRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShkafServiceImpl implements ShkafService{
    private final ShkafRepository shkafRepository ;
    @Override
    public String delete(UUID id, UUID qavatId) {

        Optional<Shkaf> byId = shkafRepository.findById(id);
        if (byId.isPresent()){

            shkafRepository.delete(byId.get());
            return "Success deleted";
        }
        else {
            throw new DataNotFoundException("Not found shkaf");
        }


    }
}
