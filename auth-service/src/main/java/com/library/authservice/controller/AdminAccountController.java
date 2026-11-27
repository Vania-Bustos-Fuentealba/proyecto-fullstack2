// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.controller.
package com.library.authservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.service.AdminAccountService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Operation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.tags.Tag;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Valid;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.security.Principal;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestController
// Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
@RequestMapping("/auth/users")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Administración de cuentas", description = "CRUD exclusivo ADMIN con identificadores de Auth")
// Nota de lectura: Declara el tipo AdminAccountController y su contrato.
public class AdminAccountController {
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final AdminAccountService accounts;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping @Operation(summary = "Listar cuentas y roles sin contraseñas")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<AdminAccountResponse> list() { return accounts.list(); }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/{id}") @Operation(summary = "Consultar una cuenta por ID de Auth")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public AdminAccountResponse get(@PathVariable Long id) { return accounts.get(id); }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping @Operation(summary = "Crear cuenta y perfil con rol ADMIN o CLIENT")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<AdminAccountResponse> create(@Valid @RequestBody AdminAccountRequest request) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(accounts.create(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PutMapping("/{id}") @Operation(summary = "Editar cuenta y sincronizar identidad del perfil")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public AdminAccountResponse update(@PathVariable Long id, @Valid @RequestBody AdminAccountRequest request, Principal actor) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return accounts.update(id, request, actor.getName());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @DeleteMapping("/{id}") @Operation(summary = "Eliminar cuenta y perfil sin confundir sus identificadores")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal actor) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        accounts.delete(id, actor.getName());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.noContent().build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
