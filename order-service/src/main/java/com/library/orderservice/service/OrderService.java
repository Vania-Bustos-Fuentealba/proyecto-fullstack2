// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.service.
package com.library.orderservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderResponseDTO;

// Nota de lectura: Declara el tipo OrderService y su contrato.
public interface OrderService {
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    OrderResponseDTO createOrder(OrderRequestDTO request);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
