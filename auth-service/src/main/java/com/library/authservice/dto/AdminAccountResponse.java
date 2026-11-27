// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.dto.
package com.library.authservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;

// ID de autenticación, nunca ID de perfil; no contiene hash ni contraseña.
// Nota de lectura: Declara el tipo AdminAccountResponse y su contrato.
public record AdminAccountResponse(Long id, String name, String email, Role role) {}
