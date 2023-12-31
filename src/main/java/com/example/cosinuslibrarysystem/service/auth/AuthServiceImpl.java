package com.example.cosinuslibrarysystem.service.auth;


import com.example.cosinuslibrarysystem.config.jwt.JwtService;
import com.example.cosinuslibrarysystem.dtos.request.LoginDTO;
import com.example.cosinuslibrarysystem.dtos.request.PasswordUpdateDTO;
import com.example.cosinuslibrarysystem.dtos.request.ResetPasswordDTO;
import com.example.cosinuslibrarysystem.dtos.request.UserCreateDTO;
import com.example.cosinuslibrarysystem.dtos.response.AuthResponseDTO;
import com.example.cosinuslibrarysystem.dtos.response.TokenDTO;
import com.example.cosinuslibrarysystem.dtos.response.UserResponseDTO;
import com.example.cosinuslibrarysystem.entity.User;
import com.example.cosinuslibrarysystem.entity.VerificationData;
import com.example.cosinuslibrarysystem.enums.UserRole;
import com.example.cosinuslibrarysystem.enums.UserStatus;
import com.example.cosinuslibrarysystem.exception.BadRequestException;
import com.example.cosinuslibrarysystem.exception.DataNotFoundException;
import com.example.cosinuslibrarysystem.exception.DuplicateValueException;
import com.example.cosinuslibrarysystem.exception.UserPasswordWrongException;
import com.example.cosinuslibrarysystem.repository.UserRepository;
import com.example.cosinuslibrarysystem.service.mail.MailSenderService;
import com.example.cosinuslibrarysystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private Map<String, User> userMap = new HashMap<>();

    //todo: check response
    @Override
    public AuthResponseDTO<UserResponseDTO> create(UserCreateDTO userCreateDTO) {
        checkEmailUnique(userCreateDTO.getEmail());
        checkUserPasswordAndIsValid(userCreateDTO.getPassword(), userCreateDTO.getConfirmPassword());
        User user = modelMapper.map(userCreateDTO, User.class);

        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.UNVERIFIED);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setVerificationData(generateVerificationData());

        userMap.put(userCreateDTO.getEmail(), user);
        String message = mailSenderService.sendVerificationCode(user.getEmail(),
                user.getVerificationData().getVerificationCode());
        UserResponseDTO mappedUser = modelMapper.map(user, UserResponseDTO.class);
        return new AuthResponseDTO<>(message, mappedUser);
    }


    @Override
    public String verify(String email, String verificationCode) {
        User user = userMap.get(email);
        if (checkVerificationCodeAndExpiration(user.getVerificationData(), verificationCode))
            return "Verification code wrong";
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        userMap.remove(email);
        return "Successfully verified";
    }


    @Override
    public String newVerifyCode(String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            user.setVerificationData(generateVerificationData());
            userRepository.save(user);
            return mailSenderService.sendVerificationCode(email, user.getVerificationData().getVerificationCode());
        } else {
            User mapUser = userMap.get(email);
            mapUser.setVerificationData(generateVerificationData());
            return mailSenderService.sendVerificationCode(email, mapUser.getVerificationData().getVerificationCode());
        }
    }


    //todo: check delete user
    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        User user = getActiveUserByEmail(loginDTO.getEmail());
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new UserPasswordWrongException("User password wrong with Password: " + loginDTO.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        return jwtService.generateToken(user.getEmail());
    }


    @Override
    public String forgotPassword(String email) {
        User user = getActiveUserByEmail(email);
        user.setVerificationData(generateVerificationData());
        userRepository.save(user);
        return mailSenderService.sendVerificationCode(email, user.getVerificationData().getVerificationCode());
    }


    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = getActiveUserByEmail(resetPasswordDTO.getEmail());
        if (checkVerificationCodeAndExpiration(user.getVerificationData(), resetPasswordDTO.getVerificationCode()))
            return "Verification code wrong";
        checkUserPasswordAndIsValid(resetPasswordDTO.getNewPassword(), resetPasswordDTO.getConfirmPassword());
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);
        return "Password successfully changed";
    }


    @Override
    public String updatePassword(PasswordUpdateDTO passwordUpdateDTO) {
        User user = getUserByID(passwordUpdateDTO.getUserID());
        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword())){
            throw new UserPasswordWrongException("Old password wrong! Password: " + passwordUpdateDTO.getOldPassword());
        }

        checkUserPasswordAndIsValid(passwordUpdateDTO.getNewPassword(), passwordUpdateDTO.getRepeatPassword());
        user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
        userRepository.save(user);
        return "Password successfully updated";
    }

    private User getUserByID(UUID userID) {
        return userRepository.findById(userID).orElseThrow(
                () -> new DataNotFoundException("User not found with ID: " + userID)
        );
    }


    private void checkEmailUnique(String email) {
        if (userRepository.existsUserByEmail(email))
            throw new DuplicateValueException("User already exists with Email: " + email);
    }


    private void checkUserPasswordAndIsValid(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword))
            throw new UserPasswordWrongException("Password must be same with confirm password: " + confirmPassword);
        checkPasswordIsValid(password);
    }


    private void checkPasswordIsValid(String password) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d).{8,20}$";
        if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("Invalid password: " + password);
        }
    }


    private VerificationData generateVerificationData() {
        Random random = new Random();
        String verificationCode = String.valueOf(random.nextInt(100000, 1000000));
        return new VerificationData(verificationCode, LocalDateTime.now());
    }


    public boolean checkVerificationCodeAndExpiration(VerificationData verificationData, String verificationCode) {
        if (!verificationData.getVerificationDate().plusMinutes(100).isAfter(LocalDateTime.now()))
            throw new BadRequestException("Verification code expired");
        return !Objects.equals(verificationData.getVerificationCode(), verificationCode);
    }


    private User getActiveUserByEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user.getStatus().equals(UserStatus.UNVERIFIED)) {
            throw new BadRequestException("User unverified");
        }
        return user;
    }

}