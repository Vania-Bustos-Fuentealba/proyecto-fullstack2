// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.pedido.
package com.library.bookservice.pedido;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Declara el tipo PedidoRepository y su contrato.
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<Pedido> findAllByOrderByFechaDesc();
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    java.util.Optional<Pedido> findBySolicitud(String solicitud);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}