package com.library.userservice.service;

import com.library.userservice.dto.UserRequestDTO;
import com.library.userservice.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    void deleteUser(Long id);

    void deleteUserByEmail(String email);
}
