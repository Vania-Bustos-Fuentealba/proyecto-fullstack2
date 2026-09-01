package com.library.authservice.service.impl;

import com.library.authservice.dto.LoginRequestDTO;
import com.library.authservice.dto.LoginResponseDTO;
import com.library.authservice.dto.RegisterRequestDTO;
import com.library.authservice.dto.UserResponseDTO;
import com.library.authservice.entity.Role;
import com.library.authservice.entity.User;
import com.library.authservice.repository.UserRepository;
import com.library.authservice.security.JwtService;
import com.library.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Override
    public UserResponseDTO registerUser(RegisterRequestDTO request) {
        logger.info("Creando nuevo usuario con email {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Intento de registro con correo duplicado: {}", request.getEmail());
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CLIENT);

        User savedUser = userRepository.save(user);

        java.util.Map<String, Object> userRequest = new java.util.HashMap<>();
        userRequest.put("name", request.getName());
        userRequest.put("email", request.getEmail());
        userRequest.put("phone", "000000000");
        userRequest.put("address", "Direccion temporal");

        String responseUserService = restTemplate.postForObject(
                "http://localhost:8089/users",
                userRequest,
                String.class
        );

        logger.info("Respuesta desde user-service: {}", responseUserService);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        logger.info("Usuario registrado correctamente con id {}", savedUser.getId());

        return response;
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Eliminando usuario con id {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado con id {}", id);
                    return new RuntimeException("Usuario no encontrado");
                });

        if ("admin@library.cl".equalsIgnoreCase(user.getEmail())) {
            logger.warn("Intento de eliminar la cuenta administradora principal");
            throw new RuntimeException("No se puede eliminar la cuenta administradora principal");
        }

        String email = user.getEmail();

        userRepository.delete(user);

        try {
            restTemplate.delete("http://user-service:8080/users/email/" + email);
            logger.info("Perfil eliminado en user-service para email {}", email);
        } catch (Exception e) {
            logger.warn("No se pudo eliminar el perfil en user-service para email {}", email);
        }

        logger.info("Usuario eliminado correctamente desde auth-service con id {}", id);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        logger.info("Intentando login para email {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado para email {}", request.getEmail());
                    return new RuntimeException("Usuario no encontrado");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Contraseña incorrecta para email {}", request.getEmail());
            throw new RuntimeException("Password incorrecta");
        }

        String token = jwtService.generateToken(user);

        logger.info("Login exitoso para email {}", request.getEmail());

        return new LoginResponseDTO(
                "Inicio de sesión exitoso",
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }
}