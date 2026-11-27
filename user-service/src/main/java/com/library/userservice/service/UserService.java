// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.service.
package com.library.userservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserResponseDTO;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo UserService y su contrato.
public interface UserService {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    UserResponseDTO createUser(UserRequestDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<UserResponseDTO> getAllUsers();

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    UserResponseDTO getUserById(Long id);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteUser(Long id);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    UserResponseDTO updateIdentity(String email, com.library.userservice.dto.IdentityUpdateDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteUserByEmail(String email);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
