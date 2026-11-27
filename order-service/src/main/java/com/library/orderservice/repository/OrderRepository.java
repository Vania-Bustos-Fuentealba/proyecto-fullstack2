// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.repository.
package com.library.orderservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.entity.OrderEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Declara el tipo OrderRepository y su contrato.
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
