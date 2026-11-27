// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.entity.
package com.library.contentservice.entity;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.AllArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Builder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Getter;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.NoArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Setter;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.time.LocalDateTime;

// Nota de lectura: Indica a JPA que esta clase representa registros persistentes.
@Entity
// Nota de lectura: Define el nombre y las restricciones de la tabla asociada.
@Table(name = "posts")
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Getter
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Setter
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@NoArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@AllArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Builder
// Nota de lectura: Declara el tipo Post y su contrato.
public class Post {

    // Nota de lectura: Marca el identificador único de cada registro.
    @Id
    // Nota de lectura: Delega la generación del identificador a la estrategia configurada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 150)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String title;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 300)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String summary;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(name = "image_url", length = 500)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String imageUrl;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(name = "image_alt", length = 150)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String imageAlt;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, columnDefinition = "TEXT")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String content;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(name = "created_at", nullable = false, updatable = false)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private LocalDateTime createdAt;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @PrePersist
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void onCreate() {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (createdAt == null) {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            createdAt = LocalDateTime.now();
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}