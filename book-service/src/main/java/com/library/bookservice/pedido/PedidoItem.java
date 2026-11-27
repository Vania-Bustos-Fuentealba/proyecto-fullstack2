// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.pedido.
package com.library.bookservice.pedido;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.entity.Book;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Column;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Entity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.FetchType;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.GeneratedValue;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.GenerationType;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Id;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.JoinColumn;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.ManyToOne;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Table;

// Nota de lectura: Indica a JPA que esta clase representa registros persistentes.
@Entity
// Nota de lectura: Define el nombre y las restricciones de la tabla asociada.
@Table(name = "pedido_items")
// Nota de lectura: Declara el tipo PedidoItem y su contrato.
public class PedidoItem {

    // Nota de lectura: Marca el identificador único de cada registro.
    @Id
    // Nota de lectura: Delega la generación del identificador a la estrategia configurada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @JoinColumn(name = "pedido_id")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Pedido pedido;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @JoinColumn(name = "libro_id")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Book libro;

    // Se guardan el nombre y el precio del momento de la compra
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 150)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String nombreLibro;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, precision = 19, scale = 2)
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.math.BigDecimal precioUnitario;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private int cantidad;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, precision = 19, scale = 2)
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.math.BigDecimal subtotal;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public Long getId() { return id; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setId(Long id) { this.id = id; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public Pedido getPedido() { return pedido; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public Book getLibro() { return libro; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setLibro(Book libro) { this.libro = libro; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getNombreLibro() { return nombreLibro; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setNombreLibro(String nombreLibro) { this.nombreLibro = nombreLibro; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public java.math.BigDecimal getPrecioUnitario() { return precioUnitario; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setPrecioUnitario(java.math.BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public int getCantidad() { return cantidad; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public java.math.BigDecimal getSubtotal() { return subtotal; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setSubtotal(java.math.BigDecimal subtotal) { this.subtotal = subtotal; }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}