// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.dto.
package com.library.authservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.AllArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.NoArgsConstructor;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@AllArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@NoArgsConstructor
// Nota de lectura: Declara el tipo LoginResponseDTO y su contrato.
public class LoginResponseDTO {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String message;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String email;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String role;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String token;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}