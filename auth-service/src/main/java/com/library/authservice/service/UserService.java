// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.service.
package com.library.authservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.LoginRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.LoginResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.RegisterRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.UserResponseDTO;

// Nota de lectura: Declara el tipo UserService y su contrato.
public interface UserService {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    UserResponseDTO registerUser(RegisterRequestDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    LoginResponseDTO login(LoginRequestDTO request);

// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
