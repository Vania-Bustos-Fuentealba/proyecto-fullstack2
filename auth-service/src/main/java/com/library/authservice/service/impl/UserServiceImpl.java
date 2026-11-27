// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.service.impl.
package com.library.authservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.LoginRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.LoginResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.RegisterRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.UserResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.User;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.security.JwtService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.service.UserService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.beans.factory.annotation.Value;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.crypto.password.PasswordEncoder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo UserServiceImpl y su contrato.
public class UserServiceImpl implements UserService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(UserServiceImpl.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final UserRepository userRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final JwtService jwtService;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PasswordEncoder passwordEncoder;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate restTemplate;

    // Nota de lectura: Lee una propiedad de configuración o variable de entorno; no escribe un secreto en el código.
    @Value("${user-service.url:http://user-service:8080}")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String userServiceUrl;

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.springframework.transaction.annotation.Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public UserResponseDTO registerUser(RegisterRequestDTO request) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        com.library.authservice.security.PasswordRules.validate(request.getPassword());
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando nuevo usuario con email {}", request.getEmail());

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        if (userRepository.existsByEmail(request.getEmail())) {
            // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
            logger.warn("Intento de registro con correo duplicado: {}", request.getEmail());
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new org.springframework.web.server.ResponseStatusException(
                    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                    org.springframework.http.HttpStatus.CONFLICT, "El correo ya está registrado");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        com.library.authservice.security.ProfileRules.validate(request);
        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        if (userRepository.existsByRun(request.getRun().toUpperCase(java.util.Locale.ROOT)))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, "El RUN ya está registrado");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User user = new User();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        com.library.authservice.security.ProfileRules.apply(request, user);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setName(request.getName());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setEmail(request.getEmail());
        // Nota de lectura: Calcula el hash BCrypt de la contraseña; no almacena la clave en texto plano.
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(Role.CLIENT);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        User savedUser = userRepository.save(user);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var userRequest = com.library.authservice.security.ProfileRules.payload(request, request.getName(), request.getEmail());
        // Conserva la URL configurable de Ale y el alta reintentable de administración.
        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        restTemplate.put(userServiceUrl + "/users/email/{email}", userRequest, request.getEmail());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserResponseDTO response = new UserResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(savedUser.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setName(savedUser.getName());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setEmail(savedUser.getEmail());

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Usuario registrado correctamente con id {}", savedUser.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public LoginResponseDTO login(LoginRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Intentando login para email {}", request.getEmail());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        User user = userRepository.findByEmail(request.getEmail())
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Usuario no encontrado para email {}", request.getEmail());
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new org.springframework.web.server.ResponseStatusException(
                            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                            org.springframework.http.HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
            logger.warn("Contraseña incorrecta para email {}", request.getEmail());
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new org.springframework.web.server.ResponseStatusException(
                    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                    org.springframework.http.HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String token = jwtService.generateToken(user);

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Login exitoso para email {}", request.getEmail());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new LoginResponseDTO(
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "Inicio de sesión exitoso",
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                user.getEmail(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                user.getRole().name(),
                // Nota de lectura: Aporta el valor token a la declaración o llamada abierta.
                token
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
