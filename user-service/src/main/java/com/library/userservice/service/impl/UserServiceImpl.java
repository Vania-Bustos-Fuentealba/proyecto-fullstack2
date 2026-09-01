package com.library.userservice.service.impl;

import com.library.userservice.dto.UserRequestDTO;
import com.library.userservice.dto.UserResponseDTO;
import com.library.userservice.entity.UserProfile;
import com.library.userservice.repository.UserRepository;
import com.library.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private UserResponseDTO mapToResponse(UserProfile user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        return response;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {
        logger.info("Creando nuevo usuario con email {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Intento de registro con correo duplicado: {}", request.getEmail());
            throw new RuntimeException("El correo ya está registrado");
        }

        UserProfile user = new UserProfile();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        UserProfile savedUser = userRepository.save(user);

        logger.info("Usuario creado correctamente con id {}", savedUser.getId());

        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        logger.info("Listando todos los usuarios");
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        logger.info("Buscando usuario con id {}", id);

        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario con id {} no encontrado", id);
                    return new RuntimeException("Usuario no encontrado");
                });

        logger.info("Usuario con id {} encontrado", id);
        return mapToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Intentando eliminar usuario con id {}", id);

        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario con id {} no encontrado para eliminar", id);
                    return new RuntimeException("Usuario no encontrado");
                });

        userRepository.delete(user);
        logger.info("Usuario con id {} eliminado correctamente", id);
    }

    @Override
    @Transactional
    public void deleteUserByEmail(String email) {
        logger.info("Eliminando usuario con email {}", email);

        if (!userRepository.existsByEmail(email)) {
            logger.warn("Usuario no encontrado con email {}", email);
            throw new RuntimeException("Usuario no encontrado");
        }

        userRepository.deleteByEmail(email);

        logger.info("Usuario eliminado correctamente con email {}", email);
    }
}