package com.library.authservice.service;

import com.library.authservice.dto.LoginRequestDTO;
import com.library.authservice.dto.LoginResponseDTO;
import com.library.authservice.dto.RegisterRequestDTO;
import com.library.authservice.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

    void deleteUser(Long id);
}
