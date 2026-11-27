// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.dto.
package com.library.authservice.dto;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;
// Nunca incluye contraseña, hash ni versión de credenciales.
// Nota de lectura: Declara el tipo AdminAccountResponse y su contrato.
public record AdminAccountResponse(Long id, String name, String email, Role role,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String run, String lastName, java.time.LocalDate birthDate, String region, String commune, String address) {
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public AdminAccountResponse(Long id, String name, String email, Role role) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        this(id, name, email, role, null, null, null, null, null, null);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
