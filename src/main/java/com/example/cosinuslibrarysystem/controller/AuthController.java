package com.example.cosinuslibrarysystem.controller;

import com.example.cosinuslibrarysystem.dtos.request.*;
import com.example.cosinuslibrarysystem.dtos.response.AuthResponseDTO;
import com.example.cosinuslibrarysystem.dtos.response.TokenDTO;
import com.example.cosinuslibrarysystem.dtos.response.UserResponseDTO;
import com.example.cosinuslibrarysystem.service.auth.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
//45bcfd1b-77d6-4b07-974f-96e895b4a9dc,2023-12-24 10:11:45.782000,2023-12-24 10:36:53.941000,false,saidazimtoirov102uz@gmail.com,xasan,xasan,$2a$10$hT8PcMLBYzO9UalB0DvUQudpenvFGuuQD3Or00iaspZSxheLYG9su,USER,ACTIVE,126327,2023-12-24 10:36:53.927848
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDTO<UserResponseDTO>> signUp(
            @Valid @RequestBody UserCreateDTO userCreateDTO
    ) {
        AuthResponseDTO<UserResponseDTO> responseDTO = authService.create(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verify(
            @Valid @RequestBody VerifyDTO verifyDTO
    ) {
        String response = authService.verify(verifyDTO.getEmail(), verifyDTO.getVerificationCode());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/new-verification-code")
    public ResponseEntity<String> newVerificationCode(
            @RequestParam String email
    ) {
        String response = authService.newVerifyCode(email);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<TokenDTO> signIn(
            @RequestBody LoginDTO loginDTO
    ) {
        System.out.println("loginDTO.getEmail() = " + loginDTO.getEmail());

        TokenDTO token = authService.login(loginDTO);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam @Email String email
    ) {
        String response = authService.forgotPassword(email);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        String response = authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO
    ) {
        return ResponseEntity.ok(authService.updatePassword(passwordUpdateDTO));
    }

}
