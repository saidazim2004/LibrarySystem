package com.example.cosinuslibrarysystem.service.user;



import com.example.cosinuslibrarysystem.dtos.response.UserResponseDTO;
import com.example.cosinuslibrarysystem.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserByID(UUID userID);

    UserResponseDTO getById(UUID id);

    User getUserByEmail(String email);

    List<UserResponseDTO> searchByEmail(String email, Integer page, Integer size);

    List<UserResponseDTO> getAll(Integer page, Integer size);

    List<UserResponseDTO> filterByRole(Integer page, Integer size, String role);

    List<UserResponseDTO> getAllDeleted(Integer page, Integer size);

    String blockByID(UUID userId);

    String unblockByID(UUID userId);


    String deleteByID(UUID userId);

    void deleteSelectedUsers(List<UUID> userIds);
}
