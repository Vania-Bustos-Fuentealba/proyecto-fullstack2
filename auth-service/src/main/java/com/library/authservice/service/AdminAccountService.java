// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.service.
package com.library.authservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.crypto.password.PasswordEncoder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.transaction.annotation.Transactional;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.server.ResponseStatusException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Locale;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Map;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo AdminAccountService y su contrato.
public class AdminAccountService {
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final UserRepository repository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final UserService registration;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PasswordEncoder encoder;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate restTemplate;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.springframework.beans.factory.annotation.Value("${user-service.url:http://user-service:8080}")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String userServiceUrl = "http://user-service:8080";

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private AdminAccountResponse response(User account) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return new AdminAccountResponse(account.getId(), account.getName(), account.getEmail(), account.getRole(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                account.getRun(), account.getLastName(), account.getBirthDate(), account.getRegion(), account.getCommune(), account.getAddress());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private User account(Long id) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return repository.findById(id).orElseThrow(() ->
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<AdminAccountResponse> list() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return repository.findAll().stream().map(this::response).toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public AdminAccountResponse get(Long id) { return response(account(id)); }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public AdminAccountResponse create(AdminAccountRequest request) {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (request.getPassword() == null || request.getPassword().isBlank())
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña inicial es obligatoria");
        // Reutiliza registro: contraseña cifrada y creación del perfil en user-service.
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        RegisterRequestDTO data = new RegisterRequestDTO(request.getName().trim(),
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                request.getEmail().trim().toLowerCase(Locale.ROOT), request.getPassword());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        com.library.authservice.security.ProfileRules.copy(request, data);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserResponseDTO created = registration.registerUser(data);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User user = account(created.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(request.getRole());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return response(repository.save(user));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public AdminAccountResponse update(Long id, AdminAccountRequest request, String actor) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        com.library.authservice.security.PasswordRules.validate(request.getPassword());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User user = account(id);
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        // Evita perder el acceso de la cuenta que está administrando.
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (user.getEmail().equalsIgnoreCase(actor) &&
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                (request.getRole() != Role.ADMIN || !email.equalsIgnoreCase(actor)))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No puedes cambiar tu propio rol ni correo desde administración");
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if ("admin@library.cl".equalsIgnoreCase(user.getEmail()) &&
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                (request.getRole() != Role.ADMIN || !email.equalsIgnoreCase(user.getEmail())))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La cuenta administradora principal debe conservar su rol y correo");
        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        if (!user.getEmail().equalsIgnoreCase(email) && repository.existsByEmail(email))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        com.library.authservice.security.ProfileRules.validate(request);
        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        if (repository.existsByRunAndIdNot(request.getRun().toUpperCase(java.util.Locale.ROOT), id))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUN ya está registrado");
        // Sincroniza por correo; los IDs de las dos bases son independientes.
        // Si el perfil falla, la transacción local no confirma la edición de Auth.
        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        restTemplate.put(userServiceUrl + "/users/email/{email}",
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                com.library.authservice.security.ProfileRules.payload(request, request.getName().trim(), email), user.getEmail());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        com.library.authservice.security.ProfileRules.apply(request, user);
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        user.setName(request.getName().trim());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setEmail(email);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(request.getRole());
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (request.getPassword() != null) {
            // Nota de lectura: Calcula el hash BCrypt de la contraseña; no almacena la clave en texto plano.
            user.setPassword(encoder.encode(request.getPassword()));
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            user.setTokenVersion(user.getTokenVersion() + 1);
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return response(repository.save(user));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void delete(Long id, String actor) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User user = account(id);
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (user.getEmail().equalsIgnoreCase(actor) || "admin@library.cl".equalsIgnoreCase(user.getEmail()))
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No puedes eliminar tu cuenta ni la administradora principal");
        // El perfil se elimina primero. Un fallo remoto no se presenta como éxito.
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        restTemplate.delete(userServiceUrl + "/users/email/{email}", user.getEmail());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        repository.delete(user);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
