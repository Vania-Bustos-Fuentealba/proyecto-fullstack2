// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.entity.
package com.library.bookservice.entity;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Indica a JPA que esta clase representa registros persistentes.
@Entity
// Nota de lectura: Define el nombre y las restricciones de la tabla asociada.
@Table(name = "books")
// Nota de lectura: Declara el tipo Book y su contrato.
public class Book {

    // Nota de lectura: Marca el identificador único de cada registro.
    @Id
    // Nota de lectura: Delega la generación del identificador a la estrategia configurada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;

    // Evita que una edición administrativa concurrente sobrescriba un descuento de stock.
    // Nota de lectura: Activa el control de concurrencia para detectar actualizaciones con una versión antigua.
    @Version
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long version;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 150)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String title;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String author;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Double price;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Integer stock;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
