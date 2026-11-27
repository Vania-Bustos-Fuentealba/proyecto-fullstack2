// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.pedido.
package com.library.bookservice.pedido;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.time.LocalDateTime;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.ArrayList;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.CascadeType;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Column;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Entity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.GeneratedValue;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.GenerationType;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Id;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.OneToMany;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.persistence.Table;

// Nota de lectura: Indica a JPA que esta clase representa registros persistentes.
@Entity
// Nota de lectura: Define el nombre y las restricciones de la tabla asociada.
@Table(name = "pedidos")
// Pedidos y existencias se confirman juntos en la base de Book.
// Nota de lectura: Declara el tipo Pedido y su contrato.
public class Pedido {
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String propietario;
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, unique = true, length = 160)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String solicitud;
    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 64)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String huella;
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getPropietario() { return propietario; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setPropietario(String value) { propietario = value; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getSolicitud() { return solicitud; }
    // Nota de lectura: Guarda la clave de este intento para reconocer reenvíos del mismo pedido.
    public void setSolicitud(String value) { solicitud = value; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getHuella() { return huella; }
    // Nota de lectura: Guarda el resumen del contenido para rechazar una clave reutilizada con otros datos.
    public void setHuella(String value) { huella = value; }


    // Nota de lectura: Marca el identificador único de cada registro.
    @Id
    // Nota de lectura: Delega la generación del identificador a la estrategia configurada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private LocalDateTime fecha;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String cliente;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String correo;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 300)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String direccion;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String comuna;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, length = 20)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String metodoPago;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(length = 20)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String cupon;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, precision = 19, scale = 2)
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.math.BigDecimal subtotal;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, precision = 19, scale = 2)
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.math.BigDecimal descuento;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, precision = 19, scale = 2)
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.math.BigDecimal envio;

    // Nota de lectura: Configura la columna que almacenará este campo en la base de datos.
    @Column(nullable = false, precision = 19, scale = 2)
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.math.BigDecimal total;

    // Un pedido tiene muchos ítems; al guardar el pedido se guardan también sus ítems
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private List<PedidoItem> items = new ArrayList<>();

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void agregarItem(PedidoItem item) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        items.add(item);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        item.setPedido(this);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public Long getId() { return id; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setId(Long id) { this.id = id; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public LocalDateTime getFecha() { return fecha; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getCliente() { return cliente; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setCliente(String cliente) { this.cliente = cliente; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getCorreo() { return correo; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setCorreo(String correo) { this.correo = correo; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getDireccion() { return direccion; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setDireccion(String direccion) { this.direccion = direccion; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getComuna() { return comuna; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setComuna(String comuna) { this.comuna = comuna; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getMetodoPago() { return metodoPago; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String getCupon() { return cupon; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setCupon(String cupon) { this.cupon = cupon; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public java.math.BigDecimal getSubtotal() { return subtotal; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setSubtotal(java.math.BigDecimal subtotal) { this.subtotal = subtotal; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public java.math.BigDecimal getDescuento() { return descuento; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setDescuento(java.math.BigDecimal descuento) { this.descuento = descuento; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public java.math.BigDecimal getEnvio() { return envio; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setEnvio(java.math.BigDecimal envio) { this.envio = envio; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public java.math.BigDecimal getTotal() { return total; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setTotal(java.math.BigDecimal total) { this.total = total; }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<PedidoItem> getItems() { return items; }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void setItems(List<PedidoItem> items) { this.items = items; }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}