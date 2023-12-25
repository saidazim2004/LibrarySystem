package com.example.cosinuslibrarysystem.controller;

import com.example.cosinuslibrarysystem.service.shkaf.ShkafService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shkaf")
public class ShkafController {
    private final ShkafService shkafService ;


    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<String> delete(@RequestParam UUID id, @RequestParam UUID qavatId){

        String res = shkafService.delete(id,qavatId);
        return ResponseEntity.ok(res);
    }


}
