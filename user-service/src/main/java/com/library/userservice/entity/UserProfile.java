// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.entity.
package com.library.userservice.entity;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.*;
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
// Nota de lectura: Indica a JPA que esta clase representa registros persistentes.
@Entity
// Nota de lectura: Define el nombre y las restricciones de la tabla asociada.
@Table(name = "users")
// Nota de lectura: Declara el tipo UserProfile y su contrato.
public class UserProfile {

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
    @Column(nullable = false, length = 20)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String phone;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 300)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String address;
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(length = 9)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String run;
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String lastName;
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.time.LocalDate birthDate;
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String region;
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String commune;
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public UserProfile(Long id, String name, String email, String phone, String address) {
        // Nota de lectura: Asigna al objeto la dependencia o el valor recibido por el método.
        this.id = id; this.name = name; this.email = email; this.phone = phone; this.address = address;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
