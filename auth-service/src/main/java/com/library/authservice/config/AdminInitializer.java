package com.library.authservice.config;

import com.library.authservice.entity.Role;
import com.library.authservice.entity.User;
import com.library.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@library.cl";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setName("Administrador");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }
}