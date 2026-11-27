// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.entity.
package com.library.authservice.entity;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.AllArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.NoArgsConstructor;

// Nota de lectura: Indica a JPA que esta clase representa registros persistentes.
@Entity
// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@AllArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@NoArgsConstructor
// Nota de lectura: Define el nombre y las restricciones de la tabla asociada.
@Table(name = "auth_users")
// Nota de lectura: Declara el tipo User y su contrato.
public class User {

    // Nota de lectura: Marca el identificador único de cada registro.
    @Id
    // Nota de lectura: Delega la generación del identificador a la estrategia configurada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String name;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, unique = true, length = 150)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String email;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 255)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String password;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Enumerated(EnumType.STRING)
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 20)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Role role;

    // Cambiar la contraseña invalida los tokens que conservan la versión anterior.
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private long tokenVersion;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public User(Long id, String name, String email, String password, Role role) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        this(id, name, email, password, role, 0L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}