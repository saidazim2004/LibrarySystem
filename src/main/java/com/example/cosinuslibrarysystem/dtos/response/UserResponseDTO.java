package com.example.cosinuslibrarysystem.dtos.response;

import com.example.cosinuslibrarysystem.enums.UserRole;
import com.example.cosinuslibrarysystem.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    private UserRole role;

    private UserStatus status;

    private Long mediaId;
}