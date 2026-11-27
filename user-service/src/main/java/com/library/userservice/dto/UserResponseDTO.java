// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.dto.
package com.library.userservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.AllArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.NoArgsConstructor;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@NoArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@AllArgsConstructor
// Nota de lectura: Declara el tipo UserResponseDTO y su contrato.
public class UserResponseDTO {
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String name;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String email;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String phone;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String address;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String run;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String lastName;
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.time.LocalDate birthDate;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String region;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String commune;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
