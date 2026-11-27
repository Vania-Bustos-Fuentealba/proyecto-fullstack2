// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.config.
package com.library.authservice.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.User;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.boot.CommandLineRunner;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.crypto.password.PasswordEncoder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Component;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Component
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo AdminInitializer y su contrato.
public class AdminInitializer implements CommandLineRunner {
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.springframework.beans.factory.annotation.Value("${ADMIN_INITIAL_PASSWORD:}")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String initialPassword;

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final UserRepository userRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PasswordEncoder passwordEncoder;

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void run(String... args) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String adminEmail = "admin@library.cl";

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        if (!userRepository.existsByEmail(adminEmail)) {
            // Nunca crear una cuenta privilegiada con una contraseña pública de ejemplo.
            // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
            if (initialPassword == null || initialPassword.length() < 12) {
                // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
                throw new IllegalStateException("Configura ADMIN_INITIAL_PASSWORD con al menos 12 caracteres para la cuenta inicial");
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            }
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            com.library.authservice.security.PasswordRules.validate(initialPassword);
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            User admin = new User();
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            admin.setName("Administrador");
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            admin.setEmail(adminEmail);
            // Nota de lectura: Calcula el hash BCrypt de la contraseña; no almacena la clave en texto plano.
            admin.setPassword(passwordEncoder.encode(initialPassword));
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            admin.setRole(Role.ADMIN);

            // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
            userRepository.save(admin);
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
