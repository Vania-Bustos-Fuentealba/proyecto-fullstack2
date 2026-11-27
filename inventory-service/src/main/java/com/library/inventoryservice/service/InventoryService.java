// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.service.
package com.library.inventoryservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryResponseDTO;

// Nota de lectura: Declara el tipo InventoryService y su contrato.
public interface InventoryService {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    InventoryResponseDTO createInventory(InventoryRequestDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    InventoryResponseDTO getInventoryByBookId(Long bookId);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}