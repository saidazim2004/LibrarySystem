package com.example.cosinuslibrarysystem.service.user;


import com.example.cosinuslibrarysystem.dtos.response.UserResponseDTO;
import com.example.cosinuslibrarysystem.entity.User;
import com.example.cosinuslibrarysystem.enums.UserRole;
import com.example.cosinuslibrarysystem.exception.DataNotFoundException;
import com.example.cosinuslibrarysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.example.cosinuslibrarysystem.enums.UserStatus.ACTIVE;
import static com.example.cosinuslibrarysystem.enums.UserStatus.BLOCKED;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public User getUserByID(UUID userID) {
        return findByID(userID);
    }

    @Override
    public UserResponseDTO getById(UUID id) {
        User user = findByID(id);
        return modelMapper.map(user, UserResponseDTO.class);
    }


    @Override
    public List<UserResponseDTO> searchByEmail(String email, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "email");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.searchByEmail(email, pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {}.getType());
    }


    @Override
    public List<UserResponseDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.findAllUsers(pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
        }.getType());
    }

    @Override
    public List<UserResponseDTO> filterByRole(Integer page, Integer size, String role) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);

        try {
            List<User> users = userRepository.filterByRole(UserRole.valueOf(role), pageable).getContent();
            return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {}.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + role);
        }
    }

    @Override
    public List<UserResponseDTO> getAllDeleted(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.findAllDeleted(pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
        }.getType());
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found with Email: " + email)
        );
    }

    public User findByID(UUID id) {
        return userRepository.getUserByID(id).orElseThrow(
                () -> new DataNotFoundException("user not found with ID: " + id));
    }

    @Override
    public String blockByID(UUID userID) {
        User user = findByID(userID);
        if(user.getStatus().equals(BLOCKED)) {
            return "User already blocked";
        }
        user.setStatus(BLOCKED);
        userRepository.save(user);
        return "User blocked";
    }

    @Override
    public String unblockByID(UUID userID) {
        User user = findByID(userID);
        if(user.getStatus().equals(ACTIVE)) {
            return "User already unblocked";
        }
        user.setStatus(ACTIVE);
        userRepository.save(user);
        return "User unblocked";
    }


    @Override
    public String deleteByID(UUID userID) {
        User user = findByID(userID);
        user.setDeleted(true);
        userRepository.save(user);
        return "user deleted";
    }

    @Override
    public void deleteSelectedUsers(List<UUID> userIDs) {
        for (UUID userID : userIDs) {
            deleteByID(userID);
        }
    }
}
