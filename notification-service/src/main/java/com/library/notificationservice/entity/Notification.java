// Nota de lectura: Ubica la clase dentro del paquete com.library.notificationservice.entity.
package com.library.notificationservice.entity;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Indica a JPA que esta clase representa registros persistentes.
@Entity
// Nota de lectura: Define el nombre y las restricciones de la tabla asociada.
@Table(name="notifications")
// Nota de lectura: Declara el tipo Notification y su contrato.
public class Notification {
    // Nota de lectura: Marca el identificador único de cada registro.
    @Id
    // Nota de lectura: Delega la generación del identificador a la estrategia configurada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long userId;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 255)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String message;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 20)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String type;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 20)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String status;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}

