// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.service.impl.
package com.library.userservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.entity.UserProfile;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.service.UserService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.transaction.annotation.Transactional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

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

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private UserResponseDTO mapToResponse(UserProfile user) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserResponseDTO response = new UserResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(user.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setName(user.getName());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setEmail(user.getEmail());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setPhone(user.getPhone());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setAddress(user.getAddress());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public UserResponseDTO createUser(UserRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando nuevo usuario con email {}", request.getEmail());

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        if (userRepository.existsByEmail(request.getEmail())) {
            // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
            logger.warn("Intento de registro con correo duplicado: {}", request.getEmail());
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new RuntimeException("El correo ya está registrado");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile user = new UserProfile();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setName(request.getName());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setEmail(request.getEmail());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setPhone(request.getPhone());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setAddress(request.getAddress());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        UserProfile savedUser = userRepository.save(user);

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Usuario creado correctamente con id {}", savedUser.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(savedUser);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<UserResponseDTO> getAllUsers() {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Listando todos los usuarios");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return userRepository.findAll()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::mapToResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public UserResponseDTO getUserById(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Buscando usuario con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        UserProfile user = userRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Usuario con id {} no encontrado", id);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new RuntimeException("Usuario no encontrado");
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Usuario con id {} encontrado", id);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(user);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deleteUser(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Intentando eliminar usuario con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        UserProfile user = userRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Usuario con id {} no encontrado para eliminar", id);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new RuntimeException("Usuario no encontrado");
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        userRepository.delete(user);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Usuario con id {} eliminado correctamente", id);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deleteUserByEmail(String email) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Eliminando usuario con email {}", email);

        // Idempotente: reintentar una eliminación parcial no debe fallar por perfil ausente.
        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        userRepository.deleteByEmail(email);

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Usuario eliminado correctamente con email {}", email);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // La relación entre Auth y perfil es el correo, no el ID autoincremental.
    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public UserResponseDTO updateIdentity(String email, com.library.userservice.dto.IdentityUpdateDTO request) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        UserProfile profile = userRepository.findByEmail(email).orElseGet(() -> {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            UserProfile created = new UserProfile();
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            created.setPhone("000000000");
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            created.setAddress("Direccion temporal");
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return created;
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        });
        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        if (!email.equalsIgnoreCase(request.email()) && userRepository.existsByEmail(request.email()))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new IllegalArgumentException("El correo ya está registrado");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        profile.setName(request.name());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        profile.setEmail(request.email());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return mapToResponse(userRepository.save(profile));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
