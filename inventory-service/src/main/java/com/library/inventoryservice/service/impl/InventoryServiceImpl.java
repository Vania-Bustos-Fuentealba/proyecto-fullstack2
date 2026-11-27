// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.service.impl.
package com.library.inventoryservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.entity.Inventory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.repository.InventoryRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.service.InventoryService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo InventoryServiceImpl y su contrato.
public class InventoryServiceImpl implements InventoryService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(InventoryServiceImpl.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final InventoryRepository inventoryRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate restTemplate;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private InventoryResponseDTO mapToResponse(Inventory inventory) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        InventoryResponseDTO response = new InventoryResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(inventory.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setBookId(inventory.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setStock(inventory.getStock());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public InventoryResponseDTO createInventory(InventoryRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando inventario para bookId {}", request.getBookId());

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responseBook = restTemplate.getForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "http://book-service:8080/books/" + request.getBookId(),
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde book-service: {}", responseBook);

        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (inventoryRepository.existsByBookId(request.getBookId())) {
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new RuntimeException("Ya existe inventario para ese libro");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Inventory inventory = new Inventory();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        inventory.setBookId(request.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        inventory.setStock(request.getStock());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Inventory savedInventory = inventoryRepository.save(inventory);

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Inventario creado correctamente con id {}", savedInventory.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(savedInventory);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public InventoryResponseDTO getInventoryByBookId(Long bookId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Buscando inventario para bookId {}", bookId);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Inventory inventory = inventoryRepository.findByBookId(bookId)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el libro con id " + bookId));

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(inventory);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}