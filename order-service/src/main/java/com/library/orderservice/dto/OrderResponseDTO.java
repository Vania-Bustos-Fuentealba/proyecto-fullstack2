// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.dto.
package com.library.orderservice.dto;

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
// Nota de lectura: Declara el tipo OrderResponseDTO y su contrato.
public class OrderResponseDTO {
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long userId;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Double total;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String status;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}