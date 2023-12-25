package com.example.cosinuslibrarysystem.service.auth;

import com.example.cosinuslibrarysystem.dtos.request.LoginDTO;
import com.example.cosinuslibrarysystem.dtos.request.PasswordUpdateDTO;
import com.example.cosinuslibrarysystem.dtos.request.ResetPasswordDTO;
import com.example.cosinuslibrarysystem.dtos.request.UserCreateDTO;
import com.example.cosinuslibrarysystem.dtos.response.AuthResponseDTO;
import com.example.cosinuslibrarysystem.dtos.response.TokenDTO;
import com.example.cosinuslibrarysystem.dtos.response.UserResponseDTO;

public interface AuthService {

    AuthResponseDTO<UserResponseDTO> create(UserCreateDTO userCreateDTO);

    String verify(String email, String verificationCode);

    String newVerifyCode(String email);

    TokenDTO login(LoginDTO loginDTO);

    String forgotPassword(String email);

    String resetPassword(ResetPasswordDTO resetPasswordDTO);

    String updatePassword(PasswordUpdateDTO passwordUpdateDTO);

}
